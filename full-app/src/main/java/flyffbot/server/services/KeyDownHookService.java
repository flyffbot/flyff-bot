package flyffbot.server.services;

import flyffbot.server.dto.GlobalHotkeyDto;
import flyffbot.server.enums.EventEnum;
import flyffbot.server.enums.KeyStatus;
import flyffbot.server.services.nativeservices.SingletonAppService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KeyDownHookService implements NativeKeyListener {
	@Autowired
	private PipelineServiceImpl pipelineService;

	@Autowired
	private  EventsServiceImpl eventsService;

	@Autowired
	private SocketMessageSenderServiceImpl socketMessageSenderService;

	private Map<Integer, KeyStatus> keyCodeStatusMap;
	private AtomicReference<List<GlobalHotkeyDto>> hotKeys;

	public KeyDownHookService(){
		log.info("KeyDown Listener - Initializing...");
		boolean isDisabled = false; //For debug only
		if(isDisabled){
			log.warn("KeyDownHookService disabled! Must be enabled!");
			return;
		}

		try {
			keyCodeStatusMap = new HashMap<>();
			// Register hotkeys:
			hotKeys = new AtomicReference<>(new ArrayList<>(List.of(
					GlobalHotkeyDto.builder()
							.event(EventEnum.ADD_PIPE)
							.keys(Set.of(NativeKeyEvent.VC_ALT_L, NativeKeyEvent.VC_A))
							.build(),
					GlobalHotkeyDto.builder()
							.event(EventEnum.REMOVE_PIPE)
							.keys(Set.of(NativeKeyEvent.VC_ALT_L, NativeKeyEvent.VC_D))
							.build()
			)));

			// Initialize keys UP status
			initializeKeyUpMap();

			GlobalScreen.setEventDispatcher(new SwingDispatchService());
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			log.error("There was a problem registering the native hook.", ex);
			SingletonAppService.releaseAndClose(1);
		}

		GlobalScreen.addNativeKeyListener(this);
		log.info("KeyDown Listener - Initialization completed!");
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		val keyCode = e.getKeyCode();
		if(!keyCodeStatusMap.containsKey(keyCode)){
			return;
		}
		keyCodeStatusMap.put(keyCode, KeyStatus.DOWN);
		log.debug("key pressed {} -> {}", keyCode, keyCodeStatusMap);
		handleEvent();
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		val keyCode = e.getKeyCode();
		if(!keyCodeStatusMap.containsKey(keyCode)){
			return;
		}
		keyCodeStatusMap.put(keyCode, KeyStatus.UP);
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}

	public void addKeyBinding(Long pipeId, int pipeIndex){
		hotKeys.getAndUpdate(list -> {
			val cpy = new ArrayList<>(list);
			cpy.add(GlobalHotkeyDto.builder()
					.event(EventEnum.TOGGLE_PAUSE)
					.pipeId(pipeId)
					.keys(Set.of(NativeKeyEvent.VC_SHIFT_L, NativeKeyEvent.VC_1 + (pipeIndex * 2)))
					.build()
			);
			cpy.add(GlobalHotkeyDto.builder()
					.event(EventEnum.USE_CUSTOM_ACTION_SLOT)
					.pipeId(pipeId)
					.keys(Set.of(NativeKeyEvent.VC_SHIFT_L, NativeKeyEvent.VC_2 + (pipeIndex * 2)))
					.build()
			);
			return cpy;
		});

		initializeKeyUpMap();

		log.debug("addKeyBinding - {} - Registered TOGGLE_PAUSE: SHIFT + {}", pipeId, (1+(pipeIndex*2)));
		log.debug("addKeyBinding - {} - Registered USE_CUSTOM_ACTION_SLOT: SHIFT + {}", pipeId, (2+(pipeIndex*2)));
	}

	private void removeKeyBinding(long pipeId){
		hotKeys.getAndUpdate(list -> {
			val cpy = new ArrayList<>(list);
			return cpy.stream()
					// Keep global hotkeys and other pipe hotkeys
					.filter(item -> item.getPipeId() != pipeId)
					.collect(Collectors.toList());
		});
		initializeKeyUpMap();
	}

	private void initializeKeyUpMap() {
		keyCodeStatusMap.clear();

		hotKeys.getAndUpdate(list -> {
			val cpy = new ArrayList<>(list);
			cpy.stream()
					.map(GlobalHotkeyDto::getKeys)
					.reduce(new HashSet<>(), (a, b) -> {
						a.addAll(b);
						return a;
					})
					.forEach(key -> keyCodeStatusMap.put(key, KeyStatus.UP));
			return cpy;
		});
	}

	private void handleEvent(){
		hotKeys.getAndUpdate(list -> {
			val cpy = new ArrayList<>(list);
			cpy.forEach(item -> {
				val match = item.getKeys()
						.stream()
						.allMatch(key -> keyCodeStatusMap.get(key) == KeyStatus.DOWN);
				if (!match) {
					return;
				}
				log.debug("Running: {}", item.getEvent());
				val pipelineId = item.getPipeId();
				switch (item.getEvent()) {
					case ADD_PIPE ->
							pipelineService.addNewPipe().ifPresent(pipe -> {
								val index = pipelineService.count() - 1;
								addKeyBinding(pipe.getId(), index);
							});
					case REMOVE_PIPE -> {
						val deletedId = pipelineService.removeLastPipe();
						removeKeyBinding(deletedId);
					}
					case TOGGLE_PAUSE -> {
						pipelineService.updateTogglePause(pipelineId);
						if(pipelineService.findPipeById(pipelineId).isPaused()){
							eventsService.stopCustomActionSlotExecution();
							pipelineService.updateCustomActionSlotRunning(pipelineId, false);
						}
					}
					case USE_CUSTOM_ACTION_SLOT -> eventsService.scheduleCustomActionSlot(pipelineId);
					default -> log.error("Unexpected action found: {}", item.getEvent());
				}
				socketMessageSenderService.sendUpdatedConfiguration();
			});
			return cpy;
		});
	}
}
