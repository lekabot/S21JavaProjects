package ex03;

import java.util.concurrent.*;


class NamedThreadFactory implements ThreadFactory {
    private static int count = 0;
    
    @Override
    public Thread newThread(Runnable r) {
        count++;
        return new Thread(r, "Thread-" + count);
    }
}
