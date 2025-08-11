// 代码生成时间: 2025-08-11 18:58:02
import io.quarkus.runtime.Quarkus;
    import io.quarkus.runtime.annotations.QuarkusMain;
    import javax.sql.DataSource;
    import io.quarkus.liquibase.LiquibaseFactory;
    import liquibase.Contexts;
    import liquibase.Liquibase;
    import liquibase.changelog.ChangeLogHistoryService;
    import liquibase.changelog.ChangeLogHistoryServiceFactory;
    import liquibase.changelog.DatabaseChangeLog;
    import liquibase.resource.ClassLoaderResourceAccessor;
    import liquibase.resource.ResourceAccessor;
    import liquibase.servicelocator.ServiceLocator;
    import liquibase.ui.UIService;

    /**
 * DatabaseMigrationTool is a Quarkus application that uses Liquibase to handle database migrations.
 * It provides a simple way to migrate the database schema to the latest version.
 */
@QuarkusMain
public class DatabaseMigrationTool {

    private static final String CHANGELOG_FILE = "db/changelog/db.changelog-master.yaml"; // Define the path to the changelog file.

    public static void main(String... args) {
        try {
            migrateDatabase();
        } catch (Exception e) {
            System.err.println("Database migration failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Migrates the database to the latest version using Liquibase.
     */
    private static void migrateDatabase() throws Exception {
        ServiceLocator serviceLocator = new ServiceLocator();
        UIService uiService = serviceLocator.getUIService();
        uiService.resetUpdateCounts();

        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        serviceLocator.setResourceAccessor(resourceAccessor);

        DatabaseChangeLog changeLog = new DatabaseChangeLog();
        changeLog.setPhysicalLocation(resourceAccessor.findResource(CHANGELOG_FILE));

        LiquibaseFactory liquibaseFactory = LiquibaseFactory.getInstance();
        Liquibase liquibase = liquibaseFactory.create();
        liquibase.setChangeLog(changeLog);
        liquibase.setResourceAccessor(resourceAccessor);
        liquibase.setTag("v1.0.0"); // Set the tag if needed

        ChangeLogHistoryServiceFactory historyServiceFactory = serviceLocator.getChangeLogHistoryServiceFactory();
        ChangeLogHistoryService historyService = historyServiceFactory.getChangeLogHistoryService(liquibase.getDatabase(), resourceAccessor);
        liquibase.setChangeLogHistoryService(historyService);

        liquibase.update(new Contexts()); // Perform the migration
        System.out.println("Database migration completed successfully.");
    }
}
