package flyffbot.server.dto.pipeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSelectedWindowRequestDto {
    private Long pipelineId;
    private String hwnd;
}
