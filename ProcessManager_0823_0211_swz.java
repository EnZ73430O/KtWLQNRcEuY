// 代码生成时间: 2025-08-23 02:11:34
package com.example.processmanager;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@QuarkusMain
public class ProcessManager implements QuarkusApplication {
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private Process process = null;

    @Override
    public int run(String... args) {
        try {
            startProcess();
        } catch (IOException e) {
            System.err.println("Failed to start process: " + e.getMessage());
            return 1;
        }
        return 0;
    }

    /**
     * Starts a new process.
     * @throws IOException if the process cannot be started.
     */
    private void startProcess() throws IOException {
        if (isRunning.get()) {
            System.out.println("Process is already running.");
            return;
        }
        ProcessBuilder builder = new ProcessBuilder("your-command-here");
        builder.redirectErrorStream(true); // Redirects error stream to the output stream.
        process = builder.start();
        isRunning.set(true);
        System.out.println("Process started with PID: " + process.pid());
    }

    /**
     * Stops the current running process.
     */
    public void stopProcess() {
        if (!isRunning.get()) {
            System.out.println("Process is not running.");
            return;
        }
        process.destroy();
        try {
            if (process.waitFor()) {
                System.out.println("Process stopped with exit code: " + process.exitValue());
            } else {
                System.out.println("Process did not terminate properly.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to wait for process to terminate: " + e.getMessage());
        }
        isRunning.set(false);
    }

    /**
     * Main entry point for the application.
     * @param args command line arguments
     */
    public static void main(String... args) {
        Quarkus.run(ProcessManager.class, args);
    }

    /**
     * Quarkus shutdown hook to stop the process on application shutdown.
     */
    @Override
    public void stop() {
        if (process != null && process.isAlive()) {
            stopProcess();
        }
    }
}
