package flyffbot;


import flyffbot.gui.FBWebView;
import flyffbot.server.services.nativeservices.SingletonAppService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            if (SingletonAppService.acquireLock()) {
                FBWebView.main();
            }
        } catch (Throwable e) {
            SingletonAppService.releaseAndClose(1);
        }
    }
}
