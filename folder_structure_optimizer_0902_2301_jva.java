// 代码生成时间: 2025-09-02 23:01:10
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.transaction.Transactional;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.QuarkusApplicationLifecycle;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

/**
 * FolderStructureOptimizer is a utility class for organizing directory structure.
 * It scans a given directory and organizes files into subdirectories based on file extensions.
 */
public class FolderStructureOptimizer implements QuarkusApplication {
    private static final Logger LOGGER = Logger.getLogger(FolderStructureOptimizer.class);

    @Inject
    QuarkusApplicationLifecycle lifecycle;

    /**
     * Main method to start the application and organize the directory structure.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new FolderStructureOptimizer().run(args);
    }

    @Override
    public int run(String... args) {
        try {
            organizeDirectoryStructure(new File(args[0]));
        } catch (IOException e) {
            LOGGER.error("Error organizing directory structure", e);
        }
        return lifecycle.exit();
    }

    /**
     * Organizes the directory structure by creating subdirectories for each file extension.
     * @param directory The directory to organize.
     * @throws IOException If an I/O error occurs.
     */
    @Transactional
    public void organizeDirectoryStructure(File directory) throws IOException {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The provided path is not a valid directory");
        }

        Arrays.stream(directory.listFiles())
            .filter(File::isFile)
            .forEach(file -> moveFileToSubdirectory(file, directory));
    }

    /**
     * Moves a file to a subdirectory based on its extension.
     * @param file The file to move.
     * @param directory The parent directory.
     * @throws IOException If an I/O error occurs.
     */
    private void moveFileToSubdirectory(File file, File directory) throws IOException {
        String extension = getFileExtension(file.getName());
        if (extension == null || extension.isEmpty()) {
            return;
        }

        File targetDirectory = new File(directory, extension);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdir();
        }

        Files.move(file.toPath(), targetDirectory.toPath().resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Retrieves the file extension from a file name.
     * @param fileName The file name to extract the extension from.
     * @return The file extension or null if none.
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
    }
}
