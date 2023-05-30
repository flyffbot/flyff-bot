package flyffbot.server.actions;

import flyffbot.server.enums.KeyStatus;
import flyffbot.server.services.CustomActionSlotServiceImpl;
import flyffbot.server.services.PipelineServiceImpl;
import flyffbot.server.services.SocketMessageSenderServiceImpl;
import flyffbot.server.services.nativeservices.NativeSendKeyService;
import flyffbot.server.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
public class CustomActionSlotActionRunner implements Runnable {
    private long pipelineId;
    private PipelineServiceImpl pipelineService;
    private SocketMessageSenderServiceImpl socketMessageSenderService;
    private CustomActionSlotServiceImpl customActionSlotService;
    private NativeSendKeyService nativeSendKeyService;

    @Override
    public void run() {
        updateRunningStatus(true);
        val pipeline = pipelineService.findPipeById(pipelineId);
        val hwnd = pipeline.getSelectedWindowHwnd();
        val actions = customActionSlotService.findByPipelineId(pipelineId);
        for(val cas : actions) {
            val keys = Stream.of(cas.getHexKeyCode0(), cas.getHexKeyCode1())
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.toList());

            nativeSendKeyService.execute(KeyStatus.DOWN, hwnd, keys);
            nativeSendKeyService.execute(KeyStatus.UP, hwnd, keys);
            Utils.safeSleep(cas.getCastTimeMs(), "Error occurred while waiting cast time for custom action slot" + cas);
        }
        updateRunningStatus(false);
    }

    private void updateRunningStatus(boolean isRunning){
        pipelineService.updateCustomActionSlotRunning(pipelineId, isRunning);
        socketMessageSenderService.sendUpdatedConfiguration();
    }
}
