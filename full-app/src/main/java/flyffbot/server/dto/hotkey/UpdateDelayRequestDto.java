package flyffbot.server.dto.hotkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDelayRequestDto {
    private long id;
    private long delayMs;
}
