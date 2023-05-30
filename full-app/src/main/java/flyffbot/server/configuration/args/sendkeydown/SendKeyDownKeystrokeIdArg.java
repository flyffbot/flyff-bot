package flyffbot.server.configuration.args.sendkeydown;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendKeyDownKeystrokeIdArg extends NativeArg {
    public SendKeyDownKeystrokeIdArg(
            @Value("${native-service.send-key-down-args.keystroke-id.name}") String name
    ){
        super(name, null);
    }
}
