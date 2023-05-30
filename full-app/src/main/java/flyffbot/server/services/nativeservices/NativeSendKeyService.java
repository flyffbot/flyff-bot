package flyffbot.server.services.nativeservices;

import flyffbot.server.configuration.NativeEventSendKeyDownArgs;
import flyffbot.server.configuration.NativeEventSendKeyUpArgs;
import flyffbot.server.configuration.args.NativeArg;
import flyffbot.server.dto.nativeapi.NativeResponse;
import flyffbot.server.enums.KeyStatus;
import flyffbot.server.mapper.NativeResponseMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class NativeSendKeyService {
    @Autowired
    NativeEventSendKeyDownArgs downArgs;
    @Autowired
    NativeEventSendKeyUpArgs upArgs;
    @Autowired
    private NativeResponseMapper mapper;

    public NativeResponse<Void> execute(KeyStatus status, String hwndId, List<String> keyCodeList) {
        try {
            val command = status == KeyStatus.UP ?
                    buildKeyUpCommand(hwndId, keyCodeList) :
                    buildKeyDownCommand(hwndId, keyCodeList);

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
            return mapper.mapVoid(result);
        }catch (Exception e){
            log.error("SendKey - "+status+" - Error occurred while running API: ", e);
            return NativeResponse.<Void>builder()
                    .success(false)
                    .error(e.getMessage())
                    .build();
        }
    }

    private List<String> buildKeyDownCommand(String hwndId, List<String> keyCodeList){
        return joinArgs(
                downArgs.getPath(),
                downArgs.getApi(),
                downArgs.getHwnd().newArg(hwndId),
                downArgs.getKeystroke(),
                keyCodeList
        );
    }

    private List<String> buildKeyUpCommand(String hwndId, List<String> keyCodeList){
        return joinArgs(
                upArgs.getPath(),
                upArgs.getApi(),
                upArgs.getHwnd().newArg(hwndId),
                upArgs.getKeystroke(),
                keyCodeList
        );
    }

    private List<String> joinArgs(
            NativeArg path,
            NativeArg api,
            NativeArg hwnd,
            NativeArg keystroke,
            List<String> keyCodeList
    ){
        return Stream.concat(
                Stream.of(path, api, hwnd),
                keyCodeList.stream().map(keystroke::newArg)
        ).map(NativeArg::toArgString).collect(Collectors.toList());
    }
}
