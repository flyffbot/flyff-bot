package flyffbot.server.services;

import flyffbot.server.dto.pipeline.ConfigurationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConfigurationServiceImpl {
    @Autowired
    private PipelineServiceImpl pipelineService;

    @Autowired
    private HotkeyServiceImpl hotkeyService;

    @Autowired
    private CustomActionSlotServiceImpl customActionSlotService;

    public List<ConfigurationDto> retrieveConfiguration(){
        return pipelineService.findAllPipes().stream().map(pipe ->
                ConfigurationDto.builder()
                        .pipeline(pipe)
                        .hotkeys(hotkeyService.findByPipelineId(pipe.getId()))
                        .customActionSlots(customActionSlotService.findByPipelineId(pipe.getId()))
                        .build()
        ).collect(Collectors.toList());
    }
}
