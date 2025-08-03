// 代码生成时间: 2025-08-04 06:56:37
import io.quarkus.runtime.Quarkus;
    import io.quarkus.runtime.annotations.QuarkusMain;
    import java.lang.management.ManagementFactory;
    import java.lang.management.MemoryMXBean;
    import java.lang.management.MemoryUsage;
    import java.util.concurrent.TimeUnit;

    /**
     * Main class for the Memory Usage Analysis application.
     */
    @QuarkusMain
    public class MemoryUsageAnalysis {

        private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        /**
         * Main method to analyze memory usage.
         * @param args Command line arguments
         */
        public static void analyzeMemoryUsage(String[] args) {
            try {
                // Print heap memory usage
                printMemoryUsage(