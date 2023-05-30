package flyffbot.server.configuration;

import flyffbot.server.configuration.args.PathArg;
import flyffbot.server.configuration.args.sendkeydown.SendKeyDownApiArg;
import flyffbot.server.configuration.args.sendkeydown.SendKeyDownKeystrokeIdArg;
import flyffbot.server.configuration.args.sendkeydown.SendKeyDownSelectedHwndIdArg;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NativeEventSendKeyDownArgs {
    @Autowired
    PathArg path;

    @Autowired
    SendKeyDownApiArg api;

    @Autowired
    SendKeyDownSelectedHwndIdArg hwnd;

    @Autowired
    SendKeyDownKeystrokeIdArg keystroke;
}
