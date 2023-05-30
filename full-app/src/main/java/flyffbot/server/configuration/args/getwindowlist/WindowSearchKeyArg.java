package flyffbot.server.configuration.args.getwindowlist;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowSearchKeyArg extends NativeArg {
    public WindowSearchKeyArg(
            @Value("${native-service.get-window-list-args.window-search-key.name}") String name,
            @Value("${native-service.get-window-list-args.window-search-key.value}") String value
    ){
        super(name, value);
    }
}
