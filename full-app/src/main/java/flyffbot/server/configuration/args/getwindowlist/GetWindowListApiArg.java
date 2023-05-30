package flyffbot.server.configuration.args.getwindowlist;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetWindowListApiArg extends NativeArg {
    public GetWindowListApiArg(
            @Value("${native-service.get-window-list-args.api.name}") String name,
            @Value("${native-service.get-window-list-args.api.value}") String value
    ){
        super(name, value);
    }
}
