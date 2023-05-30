package flyffbot.server.configuration.args;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class NativeArg {
    protected String name;
    protected String value;

    public String toArgString(){
        val pair = new ArrayList<String>();
        Optional.ofNullable(name).ifPresent(pair::add);
        toSafeValue().ifPresent(pair::add);
        return String.join("=", pair);
    }

    private Optional<String> toSafeValue(){
        return Optional.ofNullable(value)
                .map(item -> item.contains(" ") ? "'"+item+"'" : item);
    }

    public NativeArg newArg(String value){
        return new NativeArg(name, value);
    }
}
