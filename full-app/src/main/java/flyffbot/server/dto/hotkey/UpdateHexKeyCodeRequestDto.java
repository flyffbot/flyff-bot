package flyffbot.server.dto.hotkey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHexKeyCodeRequestDto {
    private long id;
    private int keyIndex;
    private String hexKeyCode;
}
