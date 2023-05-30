package flyffbot.gui;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FBExecutor {
    private static FBExecutor executor;
    private final ScheduledExecutorService executorService;

    public static ScheduledExecutorService getExecutor() {
        if(executor == null){
            executor = new FBExecutor();
        }
        return executor.getExecutorInstance();
    }

    private FBExecutor() {
        executorService = Executors.newScheduledThreadPool(2);
    }

    private ScheduledExecutorService getExecutorInstance() {
        return executorService;
    }
}
