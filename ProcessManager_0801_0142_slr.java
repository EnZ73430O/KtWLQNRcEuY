// 代码生成时间: 2025-08-01 01:42:50
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * ProcessManager is a class responsible for managing processes.
 * It allows starting, stopping, and listing processes.
 */
@QuarkusMain
@ApplicationScoped
public class ProcessManager implements QuarkusApplication {

    // A map to keep track of process states.
    private final Map<String, Process> processMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService;

    public ProcessManager() {
        // Using a cached thread pool to manage threads.
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public int run(String... args) throws Exception {
        // Handle command line arguments if needed.
        // For now, we just print the start message.
        System.out.println("Process Manager started.");
        return 0;
    }

    /**
     * Starts a new process with the given name and command line arguments.
     * @param name The name of the process.
     * @param command The command line arguments for the process.
     */
    public void startProcess(String name, String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
            processMap.put(name, processBuilder.start());            executorService.submit(() -> {
                try {
                    // Start process and wait for it to finish.
                    Process process = processMap.get(name);
                    process.waitFor();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to start process: " + name);
            e.printStackTrace();
        }
    }

    /**
     * Stops the process with the given name.
     * @param name The name of the process to stop.
     */
    public void stopProcess(String name) {
        Process process = processMap.remove(name);
        if (process != null) {
            process.destroy();
        } else {
            System.err.println("Process not found: " + name);
        }
    }

    /**
     * Lists all the processes currently being managed.
     * @return A list of process names.
     */
    public List<String> listProcesses() {
        return Arrays.asList(processMap.keySet().toArray(new String[0]));
    }

    /**
     * Stops all managed processes and shuts down the executor service.
     */
    public void shutdown() {
        for (Process process : processMap.values()) {
            process.destroy();
        }
        processMap.clear();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Event listener to handle shutdown events.
     * @param event The shutdown event.
     */
    void onStart(@Observes StartupEvent event) {
        // Additional startup logic if needed.
    }

    void onStop(@Observes ShutdownEvent event) {
        shutdown();
    }
}
