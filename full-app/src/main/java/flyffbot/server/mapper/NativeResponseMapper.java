package flyffbot.server.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import flyffbot.server.dto.nativeapi.NativeResponse;
import flyffbot.server.dto.nativeapi.WindowItem;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class NativeResponseMapper {
    @Autowired
    private ObjectMapper mapper;

    public NativeResponse<List<WindowItem>> mapListWindowItem(String source) {
        try{
            val normalizedPathSeparator = source.replaceAll("\\\\", "/");
            NativeResponse<List<WindowItem>> out = mapper.readValue(normalizedPathSeparator, new TypeReference<>(){});
            return out;
        } catch (Exception e){
            log.error("Error occurred while mapping window list response. raw value was = "+source);
            return NativeResponse.<List<WindowItem>>builder().data(List.of()).build();
        }
    }
    public NativeResponse<Void> mapVoid(String source) throws JsonProcessingException {
        return mapper.readValue(source, new TypeReference<>(){});
    }
}