// 代码生成时间: 2025-08-27 05:07:55
package com.example.database;

import io.quarkus.arc.properties.IfBuildProperty;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.CDI;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
@IfBuildProperty(name = "db.pool.enabled", stringValue = "true")
public class DatabaseConnectionPoolManager implements HealthCheck, Readiness {
    
    @ConfigProperty(name = "db.url")
    String dbUrl;
    
    @ConfigProperty(name = "db.user")
    String dbUser;
    
    @ConfigProperty(name = "db.password")
    String dbPassword;
    
    private AtomicBoolean isHealthy = new AtomicBoolean(true);
    private DataSource dataSource;

    public DatabaseConnectionPoolManager() {
        // Initialize the DataSource with configuration properties
        dataSource = CDI.current().select(DataSource.class).get();
    }

    @Override
    public HealthCheckResponse call() {
        try (Connection connection = dataSource.getConnection()) {
            // If a connection can be established, the pool is healthy
            isHealthy.set(true);
            return HealthCheckResponse.up("Database connection pool is healthy");
        } catch (SQLException e) {
            // If a connection cannot be established, the pool is unhealthy
            isHealthy.set(false);
            return HealthCheckResponse.down("Database connection pool is unhealthy");
        }
    }

    public boolean isReady() {
        // Return the current health status of the connection pool
        return isHealthy.get();
    }

    /**
     * Returns a database connection from the pool.
     *
     * @return A database connection.
     * @throws SQLException If a connection cannot be obtained from the pool.
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes a database connection and returns it to the pool.
     *
     * @param connection The connection to close and return to the pool.
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Log the error or handle it according to the application's error handling policy
            }
        }
    }
}
