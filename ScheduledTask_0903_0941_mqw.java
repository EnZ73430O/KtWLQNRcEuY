// 代码生成时间: 2025-09-03 09:41:35
import io.quarkus.scheduler.Scheduled;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A scheduled task scheduler using Quarkus framework.
 * This class demonstrates how to create a simple scheduled task that runs at a fixed interval.
 */
@ApplicationScoped
public class ScheduledTask {

    // Executor service to manage the scheduled task
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * This method is annotated with @Scheduled to indicate that it should be run on a schedule.
     * The interval is specified in cron format, in this case, every 10 seconds.
     * @throws InterruptedException if the current thread is interrupted
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void executeScheduledTask() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Scheduled task executed at: " + now);
        // Simulate some long-running task
        Thread.sleep(5000);
        // Handle potential errors
        try {
            // Some business logic here
        } catch (Exception e) {
            System.err.println("Error during scheduled task execution: " + e.getMessage());
        }
    }

    /**
     * Stops the scheduled task and shuts down the executor service.
     */
    public void stop() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
