package flyffbot.server.services.nativeservices;

import flyffbot.server.configuration.NativeEventGetWindowListArgs;
import flyffbot.server.configuration.args.NativeArg;
import flyffbot.server.dto.nativeapi.NativeResponse;
import flyffbot.server.dto.nativeapi.WindowItem;
import flyffbot.server.mapper.NativeResponseMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class NativeGetWindowListService {
    @Autowired
    NativeEventGetWindowListArgs args;
    @Autowired
    private NativeResponseMapper mapper;

    public NativeResponse<List<WindowItem>> execute() {
        try {
            val command = Stream.of(
                    args.getPath(),
                    args.getApi(),
                    args.getWindowSearchKey()
            ).map(NativeArg::toArgString).collect(Collectors.toList());

            val processBuilder = new ProcessBuilder().command(command);
            val process = processBuilder.start();
            val reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            val outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line);
                outputBuilder.append(System.getProperty("line.separator"));
            }
            val result = outputBuilder.toString();
            //log.debug("GetWindowList - Response: {}", result);
            return mapper.mapListWindowItem(result);
        }catch (Exception e){
            log.error("GetWindowList - Error occurred while running API", e);
            return NativeResponse.<List<WindowItem>>builder()
                    .success(false)
                    .error(e.getMessage())
                    .data(new ArrayList<>())
                    .build();
        }
    }
}
