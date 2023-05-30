package flyffbot.server.controller;

import flyffbot.server.configuration.WebSocketConfig;
import flyffbot.server.dto.hotkey.*;
import flyffbot.server.dto.pipeline.ConfigurationDto;
import flyffbot.server.services.ConfigurationServiceImpl;
import flyffbot.server.services.HotkeyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class HotkeyController {
    @Autowired
    private HotkeyServiceImpl hotkeyService;
    @Autowired
    private ConfigurationServiceImpl configurationService;

    @MessageMapping("/post-hotkey")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateSelectedWindow(CreateHotkeyDto request) {
        hotkeyService.addHotkey(request.getPipelineId());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-hotkey-hex-key-code")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateHexKeyCode(UpdateHexKeyCodeRequestDto request) {
        hotkeyService.updateHexKeyCode(request.getId(), request.getKeyIndex(), request.getHexKeyCode());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-hotkey-delay")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateDelay(UpdateDelayRequestDto request) {
        hotkeyService.updateDelay(request.getId(), request.getDelayMs());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-hotkey-active")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateActive(UpdateActiveRequestDto request) {
        hotkeyService.updateActive(request.getId(), request.isActive());
        return configurationService.retrieveConfiguration();
    }


    @MessageMapping("/delete-hotkey")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> deleteHotkey(DeleteHotkeyDto request) {
        hotkeyService.deleteById(request.getId());
        return configurationService.retrieveConfiguration();
    }
}
