// 代码生成时间: 2025-08-29 20:25:53
package com.example.memoryanalysis;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.AttributeNotFoundException;import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RegisterForReflection
public class MemoryUsageAnalysis {

    private static final Logger LOGGER = Logger.getLogger(MemoryUsageAnalysis.class.getName());

    @Inject
    MBeanServer mBeanServer; // MBeanServer for accessing memory stats

    // Method to retrieve memory usage statistics
    @Retry(fallbackMethod = "fallbackMethod") // Retry mechanism
    public Map<String, String> getMemoryUsageStats() {
        Map<String, String> memoryStats = new HashMap<>();
        try {
            ObjectName name = new ObjectName(ManagementFactory.MEMORY_MXBEAN_NAME);
            MemoryUsage heapMemoryUsage = (MemoryUsage) mBeanServer.getAttribute(name, "HeapMemoryUsage");
            MemoryUsage nonHeapMemoryUsage = (MemoryUsage) mBeanServer.getAttribute(name, "NonHeapMemoryUsage");

            // Add heap memory stats to the map
            memoryStats.put("HeapInit", String.valueOf(heapMemoryUsage.getInit()));
            memoryStats.put("HeapUsed", String.valueOf(heapMemoryUsage.getUsed()));
            memoryStats.put("HeapCommitted", String.valueOf(heapMemoryUsage.getCommitted()));
            memoryStats.put("HeapMax", String.valueOf(heapMemoryUsage.getMax()));

            // Add non-heap memory stats to the map
            memoryStats.put("NonHeapInit", String.valueOf(nonHeapMemoryUsage.getInit()));
            memoryStats.put("NonHeapUsed", String.valueOf(nonHeapMemoryUsage.getUsed()));
            memoryStats.put("NonHeapCommitted", String.valueOf(nonHeapMemoryUsage.getCommitted()));
            memoryStats.put("NonHeapMax", String.valueOf(nonHeapMemoryUsage.getMax()));

        } catch (InstanceNotFoundException | MalformedObjectNameException | AttributeNotFoundException | ReflectionException e) {
            LOGGER.severe("Error retrieving memory usage statistics: " + e.getMessage());
        }
        return memoryStats;
    }

    // Fallback method for retry mechanism
    @Fallback
    public Map<String, String> fallbackMethod() {
        Map<String, String> memoryStats = new HashMap<>();
        memoryStats.put("Error", "Failed to retrieve memory usage statistics");
        return memoryStats;
    }

    // Main method for running the program
    public static void main(String[] args) {
        MemoryUsageAnalysis analysis = new MemoryUsageAnalysis();
        Map<String, String> stats = analysis.getMemoryUsageStats();
        stats.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
