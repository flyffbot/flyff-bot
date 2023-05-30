package flyffbot.server.dto.nativeapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WindowItem {
    private String title;
    private String hwnd;
    private boolean isNull;

    public void setIsNull(boolean value) {
        this.isNull = value;
    }
}
