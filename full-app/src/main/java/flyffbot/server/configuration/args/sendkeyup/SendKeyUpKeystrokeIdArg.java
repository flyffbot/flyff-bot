package flyffbot.server.configuration.args.sendkeyup;

import flyffbot.server.configuration.args.NativeArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendKeyUpKeystrokeIdArg extends NativeArg {
    public SendKeyUpKeystrokeIdArg(
            @Value("${native-service.send-key-up-args.keystroke-id.name}") String name
    ){
        super(name, null);
    }
}
