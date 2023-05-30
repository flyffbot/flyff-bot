package flyffbot.server.exceptions;

public class PipeConfigNotFound extends RuntimeException{
    public PipeConfigNotFound(String message, Exception e){
        super(message, e);
    }
    public PipeConfigNotFound(String message){
        super(message);
    }
}
