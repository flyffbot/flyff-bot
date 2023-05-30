package flyffbot.server.dto.customactionslot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCastTimeRequestDto {
    private long id;
    private long castTimeMs;
}
