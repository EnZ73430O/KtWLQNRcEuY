// 代码生成时间: 2025-10-07 22:00:47
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// 定义系统备份恢复工具的REST服务
@Path("/backup")
@QuarkusMain
public class BackupRestoreTool {

    // 注入文件系统访问服务
    @Inject
    private FileSystemAccess fileSystemAccess;

    // 获取当前目录下的文件列表
    @GET
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin") // 只允许管理员访问
    public List<String> listFiles() {
        try {
            return Files.list(Paths.get("."))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list files", e);
        }
    }

    // 执行备份操作
    @GET
    @Path("/backup/{name}")
    @RolesAllowed("admin") // 只允许管理员执行备份
    public String backup(String name) {
        try {
            Path sourcePath = Paths.get(".");
            Path targetPath = Paths.get("./backup_" + name);
            fileSystemAccess.copyDirectory(sourcePath, targetPath);
            return "Backup successful";
        } catch (IOException e) {
            throw new RuntimeException("Backup failed", e);
        }
    }

    // 执行恢复操作
    @GET
    @Path("/restore/{name}")
    @RolesAllowed("admin") // 只允许管理员执行恢复
    public String restore(String name) {
        try {
            Path sourcePath = Paths.get("./backup_" + name);
            Path targetPath = Paths.get(".");
            fileSystemAccess.copyDirectory(sourcePath, targetPath);
            return "Restore successful";
        } catch (IOException e) {
            throw new RuntimeException("Restore failed", e);
        }
    }
}

// 文件系统访问服务的接口定义
interface FileSystemAccess {
    void copyDirectory(Path source, Path target) throws IOException;
}

// 文件系统访问服务的实现
class FileSystemAccessImpl implements FileSystemAccess {
    @Override
    public void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source).forEach(path -> {
            try {
                Path targetPath = target.resolve(source.relativize(path).toString());
                if (Files.isDirectory(path)) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.copy(path, targetPath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy file", e);
            }
        });
    }
}