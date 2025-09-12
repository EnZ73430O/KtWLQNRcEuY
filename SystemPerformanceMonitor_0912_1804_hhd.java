// 代码生成时间: 2025-09-12 18:04:20
package com.example.monitoring;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import javax.management.MBeanServer;
# 添加错误处理
import javax.management.ObjectName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
# 优化算法效率
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
# 添加错误处理

@Path("/monitor")
@RegisterForReflection
public class SystemPerformanceMonitor {
# TODO: 优化性能

    // MBeanServer for getting runtime information
    private final MBeanServer mBeanServer;

    public SystemPerformanceMonitor() {
        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    /**
     * Get memory usage details.
     *
     * @return Memory details in JSON format.
     */
    @GET
    @Path("/memory")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long heapUsed = memoryMXBean.getHeapMemoryUsage().getUsed();
        long nonHeapUsed = memoryMXBean.getNonHeapMemoryUsage().getUsed();
        return String.format("{"heapUsed": %d, "nonHeapUsed": %d}", heapUsed, nonHeapUsed);
    }

    /**
     * Get runtime information.
     *
     * @return Runtime information in JSON format.
     */
    @GET
    @Path("/runtime")
    @Produces(MediaType.APPLICATION_JSON)
# TODO: 优化性能
    public String getRuntimeInfo() {
        try {
            ObjectName runtimeObjectName = new ObjectName("java.lang:type=Runtime");
            Long uptime = (Long) mBeanServer.getAttribute(runtimeObjectName, "Uptime");
            Long processors = (Long) mBeanServer.getAttribute(runtimeObjectName, "AvailableProcessors");
            return String.format("{"uptime": "%d", "processors": "%d"}", uptime, processors);
# 扩展功能模块
        } catch (Exception e) {
            // Handle exception and return error message
            return String.format("{"error": "%s"}", e.getMessage());
# FIXME: 处理边界情况
        }
    }

    /**
# TODO: 优化性能
     * Get thread information.
     *
     * @return Thread information in JSON format.
     */
    @GET
    @Path("/threads")
    @Produces(MediaType.APPLICATION_JSON)
# 优化算法效率
    public String getThreadInfo() {
        try {
            ObjectName threadObjectName = new ObjectName("java.lang:type=Threading");
# 优化算法效率
            Long threadCount = (Long) mBeanServer.getAttribute(threadObjectName, "ThreadCount");
            Long peakThreadCount = (Long) mBeanServer.getAttribute(threadObjectName, "PeakThreadCount");
            Long daemonThreadCount = (Long) mBeanServer.getAttribute(threadObjectName, "DaemonThreadCount");
            return String.format("{"threadCount": "%d", "peakThreadCount": "%d", "daemonThreadCount": "%d"}",
                threadCount, peakThreadCount, daemonThreadCount);
        } catch (Exception e) {
            // Handle exception and return error message
            return String.format("{"error": "%s"}", e.getMessage());
        }
    }
}
