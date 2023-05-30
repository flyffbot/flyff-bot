package flyffbot.server.configuration.args.sendkeydown;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendKeyDownApiArg extends NativeArg {
    public SendKeyDownApiArg(
            @Value("${native-service.send-key-down-args.api.name}") String name,
            @Value("${native-service.send-key-down-args.api.value}") String value
    ){
        super(name, value);
    }
}
