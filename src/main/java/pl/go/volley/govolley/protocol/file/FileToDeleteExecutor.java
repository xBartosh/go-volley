package pl.go.volley.govolley.protocol.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileToDeleteExecutor {

    private final Logger LOGGER = LoggerFactory.getLogger(FileToDeleteExecutor.class);
    private final Queue<File> fileToDeleteQueue;
    private final ScheduledExecutorService executor;
    private final Long delay;
    private final TimeUnit timeUnit;

    public FileToDeleteExecutor(Long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
        fileToDeleteQueue = new ConcurrentLinkedQueue<>();
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void add(File file) {
        fileToDeleteQueue.add(file);
        LOGGER.info("Added to queue - file with path: {}. Going to be deleted in {} {}", file.getPath(), delay, timeUnit);
        executor.schedule(() -> {
            LOGGER.info("Going to delete file with path: {}", file.getPath());
            File removed = fileToDeleteQueue.poll();
            if (removed != null) {
                boolean delete = removed.delete();
                if (delete) {
                    LOGGER.info("Successfully deleted file with path: {}", file.getPath());
                } else {
                    LOGGER.error("Could not delete file with path: {}", file.getPath());
                }
            } else {
                LOGGER.warn("Cannot delete file! The queue is empty!");
            }
        }, delay, timeUnit);
    }
}
