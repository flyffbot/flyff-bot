package flyffbot.server.services;

import flyffbot.server.actions.CustomActionSlotActionRunner;
import flyffbot.server.actions.HotkeyActionRunner;
import flyffbot.server.services.nativeservices.NativeSendKeyService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class EventsServiceImpl {
    @Autowired
    private PipelineServiceImpl pipelineService;
    @Autowired
    private HotkeyServiceImpl hotkeyService;
    @Autowired
    private CustomActionSlotServiceImpl customActionSlotService;

    @Autowired
    private NativeSendKeyService nativeSendKeyService;

    @Autowired
    private ScheduledExecutorService executorService;

    @Autowired
    private SocketMessageSenderServiceImpl socketMessageSenderService;

    private AtomicReference<ScheduledFuture<?>> casThread;

    public EventsServiceImpl() {
        casThread = new AtomicReference<>();
    }

    public void initScheduler(){
        val worker = new HotkeyActionRunner(
                pipelineService,
                hotkeyService,
                nativeSendKeyService,
                executorService
        );
        executorService.schedule(worker, 0, TimeUnit.MILLISECONDS);
    }

    public void scheduleCustomActionSlot(long pipelineId){
        val pipeline = pipelineService.findPipeById(pipelineId);
        if(pipeline.isCustomActionSlotRunning()) {
            log.debug("Unable to start custom action slot for pipelined={} - Already running!", pipeline);
            return;
        }
        val worker = new CustomActionSlotActionRunner(
                pipelineId,
                pipelineService,
                socketMessageSenderService,
                customActionSlotService,
                nativeSendKeyService
        );
        casThread.set(executorService.schedule(worker, 0, TimeUnit.MILLISECONDS));
    }

    public void stopCustomActionSlotExecution() {
        Optional.of(casThread)
                .map(AtomicReference::get)
                .ifPresent(cas -> cas.cancel(true));
    }
}
