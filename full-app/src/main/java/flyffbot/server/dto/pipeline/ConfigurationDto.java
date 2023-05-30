package flyffbot.server.dto.pipeline;

import flyffbot.server.entity.CustomActionSlotEntity;
import flyffbot.server.entity.HotkeyEntity;
import flyffbot.server.entity.PipelineEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto {
    private PipelineEntity pipeline;
    private List<HotkeyEntity> hotkeys;
    private List<CustomActionSlotEntity> customActionSlots;
}
