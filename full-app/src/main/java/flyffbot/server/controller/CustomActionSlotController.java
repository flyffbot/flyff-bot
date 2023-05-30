package flyffbot.server.controller;

import flyffbot.server.configuration.WebSocketConfig;
import flyffbot.server.dto.customactionslot.CreateCustomActionSlotDto;
import flyffbot.server.dto.customactionslot.DeleteCustomActionSlotDto;
import flyffbot.server.dto.customactionslot.UpdateCastTimeRequestDto;
import flyffbot.server.dto.customactionslot.UpdateHexKeyCodeRequestDto;
import flyffbot.server.dto.pipeline.ConfigurationDto;
import flyffbot.server.services.ConfigurationServiceImpl;
import flyffbot.server.services.CustomActionSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomActionSlotController {
    @Autowired
    private CustomActionSlotServiceImpl service;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @MessageMapping("/post-custom-action-slot")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> create(CreateCustomActionSlotDto request) {
        service.addCustomActionSlot(request.getPipelineId());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-custom-action-slot-hex-key-code")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateHexKeyCode(UpdateHexKeyCodeRequestDto request) {
        service.updateCustomActionSlot(request.getId(), request.getKeyIndex(), request.getHexKeyCode());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/put-custom-action-slot-cast-time")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> updateDelay(UpdateCastTimeRequestDto request) {
        service.updateCastTime(request.getId(), request.getCastTimeMs());
        return configurationService.retrieveConfiguration();
    }

    @MessageMapping("/delete-custom-action-slot")
    @SendTo(WebSocketConfig.UPDATED_CONFIGURATIONS_TOPIC)
    public List<ConfigurationDto> delete(DeleteCustomActionSlotDto request) {
        service.deleteById(request.getId());
        return configurationService.retrieveConfiguration();
    }
}
