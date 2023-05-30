package flyffbot.server.exceptions;

public class HotkeyNotFound extends RuntimeException{
    public HotkeyNotFound(String message, Exception e){
        super(message, e);
    }
    public HotkeyNotFound(String message){
        super(message);
    }
}
