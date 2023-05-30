package flyffbot.server.exceptions;

public class DelayConfigException extends RuntimeException{
    public DelayConfigException(String message, Exception e){
        super(message, e);
    }
    public DelayConfigException(String message){
        super(message);
    }
}
