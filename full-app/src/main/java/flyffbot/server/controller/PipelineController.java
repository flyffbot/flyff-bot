package flyffbot.server.controller;

import flyffbot.server.configuration.WebSocketConfig;
import flyffbot.server.dto.pipeline.ConfigurationDto;
import flyffbot.server.dto.pipeline.UpdateSelectedWindowRequestDto;
import flyffbot.server.services.ConfigurationServiceImpl;
import flyffbot.server.services.PipelineServiceImpl;
import flyffbot.server.services.SocketMessageSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PipelineController {
    @Value("${socket.topics.updated-configuration}")
    private String updatedConfigurationTopic;
    @Autowired
    private PipelineServiceImpl pipelineService;
    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Autowired
    private SocketMessageSenderServiceImpl socketMessageSenderService;

    @MessageMapping("/get-configuration")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> retrieveConfiguration() {
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-selected-window")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateSelectedWindow(UpdateSelectedWindowRequestDto args) {
        pipelineService.saveSelectedWindowHwnd(args.getPipelineId(), args.getHwnd());
        return configurationService.retrieveConfiguration();
    }

}