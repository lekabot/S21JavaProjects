package ex03;

import java.util.*;
import java.util.concurrent.*;

class DownloadManager {
    private final Queue<String> urlQueue;
    private final ExecutorService executor;

    public DownloadManager(List<String> urls, int threadsCount) {
        this.urlQueue = new ConcurrentLinkedQueue<>(urls);
        this.executor = Executors.newFixedThreadPool(threadsCount, new NamedThreadFactory());
    }

    public void startDownload() {
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            if (url != null) {
                executor.submit(new FileDownloader(url));
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Download interrupted: " + e.getMessage());
        }
    }
}
