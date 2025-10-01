// 代码生成时间: 2025-10-01 19:24:47
import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

@Path("/pool")
@ApplicationScoped
public class DatabaseConnectionPoolManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionPoolManager.class.getName());
    private DataSource dataSource;

    // CDI constructor injection for DataSource
    public DatabaseConnectionPoolManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Observe the StartupEvent to perform initialization when the application starts
    public void onStart(@Observes StartupEvent event) {
        // Initialize the connection pool when the application starts
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        // Example of initializing the connection pool with some settings
        // This is a placeholder for actual initialization logic
        LOGGER.info("Initializing the database connection pool...");
    }

    // A REST endpoint to demonstrate a connection from the pool
    @GET
    @Path("/check")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String checkConnection() {
        try (Connection connection = dataSource.getConnection()) {
            // This is just a simple check to open and close a connection from the pool
            Statement stmt = connection.createStatement();
            return "Connection successfully checked from pool.";
        } catch (SQLException e) {
            // Log and handle the error
            LOGGER.severe("Error checking connection from pool: " + e.getMessage());
            return "Error checking connection from pool.";
        }
    }
}
