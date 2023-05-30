package flyffbot.server.configuration.args.sendkeydown;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendKeyDownSelectedHwndIdArg extends NativeArg {
    public SendKeyDownSelectedHwndIdArg(
            @Value("${native-service.send-key-down-args.selected-hwnd-id.name}") String name
    ){
        super(name, null);
    }
}
