package flyffbot.server.dto;

import flyffbot.server.enums.EventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalHotkeyDto {
    private EventEnum event;
    private Set<Integer> keys;
    private long pipeId;
}
