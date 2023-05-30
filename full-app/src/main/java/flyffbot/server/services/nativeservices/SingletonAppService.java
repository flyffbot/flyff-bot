package flyffbot.server.services.nativeservices;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@UtilityClass
public class SingletonAppService {
    public static final String LOCK_FILE = "flyffbot.lock";

    public boolean acquireLock(){
        try {
            val lockFile = new File(LOCK_FILE);
            if (lockFile.createNewFile()) {
                return true;
            }

            for (var i = 0; i < 5; i++) {
                Thread.sleep(1000);
                if (isAlreadyRunning()) {
                    return false;
                }
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void releaseAndClose(int exitCode) {
        val lockFile = new File(LOCK_FILE);
        if(lockFile.exists()){
            System.out.println("Lock deleted: "+ lockFile.delete());
        }
        System.exit(exitCode);
    }

    private boolean isAlreadyRunning(){
        try {
            val command = "tasklist /v";
            val process = Runtime.getRuntime().exec(command);
            val reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("FlyffBot")) {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
