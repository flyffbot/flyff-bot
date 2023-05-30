package flyffbot.server.dto.nativeapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NativeResponse <T> {
    private boolean success;
    private String error;
    private T data;
}
