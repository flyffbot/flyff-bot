package flyffbot.server.configuration;

import flyffbot.server.configuration.args.PathArg;
import flyffbot.server.configuration.args.sendkeyup.SendKeyUpApiArg;
import flyffbot.server.configuration.args.sendkeyup.SendKeyUpKeystrokeIdArg;
import flyffbot.server.configuration.args.sendkeyup.SendKeyUpSelectedHwndIdArg;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NativeEventSendKeyUpArgs {
    @Autowired
    PathArg path;

    @Autowired
    SendKeyUpApiArg api;

    @Autowired
    SendKeyUpSelectedHwndIdArg hwnd;

    @Autowired
    SendKeyUpKeystrokeIdArg keystroke;
}
