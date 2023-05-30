package flyffbot.gui;

import com.sun.javafx.webkit.WebConsoleListener;
import flyffbot.server.LocalServer;
import flyffbot.server.services.nativeservices.SingletonAppService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FBWebView extends Application {
    private static WebEngine webEngine;

    public static void main() {
        try {
            log.info("WebView - starting...");
            FBExecutor.getExecutor().schedule(() -> launch(), 0, TimeUnit.SECONDS);
            log.info("Local server - starting...!");
            LocalServer.run(() -> {
                Platform.runLater(() -> webEngine.load("http://localhost:8899"));
                log.info("Local server - started!");
            });
            log.info("WebView - terminated!");
        } catch (Exception e){
            log.info("Error"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        val webView = new javafx.scene.web.WebView();
        retrieveIcon().ifPresent(icon -> stage.getIcons().add(icon));
        webEngine = webView.getEngine();
        WebConsoleListener.setDefaultListener((webView2, message, lineNumber, sourceId) -> {
            log.debug("console.log - sourceId={} message=\n{}", sourceId, message);
        });

        webEngine.loadContent(retrieveLoadingScreenHtml());

        // Optional: Listen to the worker's state property to know when the HTML content is fully loaded
        val worker = webEngine.getLoadWorker();
        worker.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                log.info("HTML content loaded successfully!");
            }
        });

        val scene = new Scene(webView, 1050, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlyffBot");
        stage.setOnCloseRequest(event -> {
            log.info("LocalServer - Closing...");
            LocalServer.close();
            log.info("LocalServer - Closed!");
            SingletonAppService.releaseAndClose(0);
        });
        stage.show();
    }

    private String retrieveLoadingScreenHtml() {
        String out = null;
        try(val inputStream = LocalServer.class.getClassLoader().getResourceAsStream("LoadingScreen.html")){
            assert inputStream != null;
            out = IOUtils.toString(inputStream, Charset.defaultCharset());
            IOUtils.closeQuietly(inputStream);
        }catch (Exception e){
            log.error("Error occurred while reading static loading screen", e);
        }
        return out;
    }

    private Optional<Image> retrieveIcon(){
        Optional<Image> image;
        try(val inputStream = LocalServer.class.getClassLoader().getResourceAsStream("FlyffBot.png")){
            image = Optional.ofNullable(inputStream).map(Image::new);
            IOUtils.closeQuietly(inputStream);
        }catch (Exception e){
            log.error("Error occurred while reading application icon", e);
            image = Optional.empty();
        }
        return image;
    }
}