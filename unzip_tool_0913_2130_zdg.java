// 代码生成时间: 2025-09-13 21:30:58
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipTool {
    
    /**
     * Unzips a file from a zip archive to a specified directory.
     *
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where the files will be unzipped.
# 优化算法效率
     * @throws IOException If an I/O error occurs.
     */
# 添加错误处理
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // 遍历zip内的文件
# 增强安全性
            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // 如果是文件，则解压
                    extractFile(zipIn, filePath);
                } else {
                    // 如果是目录，则创建目录
                    Files.createDirectories(Paths.get(filePath));
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
# 添加错误处理
        }
    }

    /**
     * Extracts a file from the zip input stream to the specified file path.
     *
     * @param zipIn The zip input stream.
     * @param filePath The path to extract the file to.
# 优化算法效率
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
# NOTE: 重要实现细节

    public static void main(String[] args) {
# 添加错误处理
        UnzipTool unzipper = new UnzipTool();
        try {
            String zipFilePath = 