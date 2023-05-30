package flyffbot.server.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class Utils {
    public static <T> Optional<Integer> findIndex(List<T> list, Predicate<T> predicate){
        for(int i = 0; i < list.size(); i++){
            if(Optional.of(list.get(i)).filter(predicate).isPresent()){
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public static String toHexString(int hexValue){
        return hexValue == -1 ? "" : "0x"+Integer.toHexString(hexValue);
    }

    public static void safeSleep(long time, String error){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e){
            log.error(error, e);
        }
    }
}
