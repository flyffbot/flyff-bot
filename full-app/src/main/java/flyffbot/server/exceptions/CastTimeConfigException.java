package flyffbot.server.exceptions;

public class CastTimeConfigException extends RuntimeException{
    public CastTimeConfigException(String message, Exception e){
        super(message, e);
    }
    public CastTimeConfigException(String message){
        super(message);
    }
}
