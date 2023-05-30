package flyffbot.server.configuration;

import flyffbot.server.configuration.args.PathArg;
import flyffbot.server.configuration.args.getwindowlist.GetWindowListApiArg;
import flyffbot.server.configuration.args.getwindowlist.WindowSearchKeyArg;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NativeEventGetWindowListArgs {
    @Autowired
    PathArg path;

    @Autowired
    GetWindowListApiArg api;

    @Autowired
    WindowSearchKeyArg windowSearchKey;
}
