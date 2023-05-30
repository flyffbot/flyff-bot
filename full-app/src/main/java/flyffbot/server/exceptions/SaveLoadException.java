package flyffbot.server.exceptions;

public class SaveLoadException extends RuntimeException{
    public SaveLoadException(String message, Exception e){
        super(message, e);
    }
    public SaveLoadException(String message){
        super(message);
    }
}
