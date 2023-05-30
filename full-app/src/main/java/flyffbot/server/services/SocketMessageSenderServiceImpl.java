package flyffbot.server.services;

import flyffbot.server.configuration.WebSocketConfig;
import flyffbot.server.services.nativeservices.NativeGetWindowListService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketMessageSenderServiceImpl {

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Autowired
    private NativeGetWindowListService nativeGetWindowListService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendUpdatedConfiguration(){
        val payload = configurationService.retrieveConfiguration();
        messagingTemplate.convertAndSend(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC, payload);
    }

    public void sendWindowList(){
        val payload = nativeGetWindowListService.execute();
        messagingTemplate.convertAndSend(WebSocketConfig.UPDATED_WINDOW_LIST, payload.getData());
    }
}
