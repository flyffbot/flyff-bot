package flyffbot.server.actions;

import flyffbot.server.entity.HotkeyEntity;
import flyffbot.server.entity.PipelineEntity;
import flyffbot.server.enums.KeyStatus;
import flyffbot.server.services.HotkeyServiceImpl;
import flyffbot.server.services.PipelineServiceImpl;
import flyffbot.server.services.nativeservices.NativeSendKeyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
public class HotkeyActionRunner implements Runnable {
    public static final long MAX_WAIT_TIME = 1000L;
    public static final long MIN_WAIT_TIME = 350L;
    private PipelineServiceImpl pipelineService;
    private HotkeyServiceImpl hotkeyService;
    private NativeSendKeyService nativeSendKeyService;
    private ScheduledExecutorService executorService;

    @Override
    public void run() {
        val now = System.currentTimeMillis();
        val pipelineMap = new HashMap<Long, PipelineEntity>();
        pipelineService.findAllPipes().forEach(pipeline -> pipelineMap.put(pipeline.getId(), pipeline));

        hotkeyService.findAllActiveOrderByNextExecutionTimeMsAsc().stream()
                .filter(hotkey -> isPipelineReady(pipelineMap, hotkey) && hotkey.getNextExecutionTimeMs() < now)
                .forEach(hotkey -> {
                    val pipeline = pipelineMap.get(hotkey.getPipelineId());
                    val hwnd = pipeline.getSelectedWindowHwnd();
                    val keys = Stream.of(hotkey.getHexKeyCode0(), hotkey.getHexKeyCode1())
                            .filter(StringUtils::isNotBlank)
                            .collect(Collectors.toList());
                    nativeSendKeyService.execute(KeyStatus.DOWN, hwnd, keys);
                    nativeSendKeyService.execute(KeyStatus.UP, hwnd, keys);
                    hotkeyService.updateLastTimeExecutedMs(hotkey.getId(), System.currentTimeMillis());
                });

        val nextRunTimeMs = hotkeyService.findAllActiveOrderByNextExecutionTimeMsAsc()
                .stream()
                .findFirst()
                .map(HotkeyEntity::getDelayMs)
                .orElse(MAX_WAIT_TIME);

        // Avoid infinite wait time if user set 99999999ms and then 500ms
        // In this scenario next run will occur after 99999999ms!
        // Keep a MIN_WAIT_TIME < 'timeM' < MAX_WAIT_TIME
        val timeMs = Math.max(Math.min(nextRunTimeMs, MAX_WAIT_TIME), MIN_WAIT_TIME);
        //log.debug("HotKey worker checks scheduling next run {}ms", timeMs);
        executorService.schedule(this, timeMs, TimeUnit.MILLISECONDS);
    }

    private boolean isPipelineReady(Map<Long, PipelineEntity> map, HotkeyEntity hotkey){
        val pipeline = map.get(hotkey.getPipelineId());
        return !(pipeline.isPaused() || StringUtils.isBlank(pipeline.getSelectedWindowHwnd()));
    }
}
