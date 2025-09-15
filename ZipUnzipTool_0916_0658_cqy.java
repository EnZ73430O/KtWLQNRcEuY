// 代码生成时间: 2025-09-16 06:58:10
package com.example.ziputils;

import io.smallrye.mutiny.Uni;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ZipUnzipTool {

    /**
     * Compress files into a zip file.
     *
     * @param filesPaths Array of paths to files to compress.
     * @param outputZipPath Path to the output zip file.
     * @return A Uni signal indication of the completion of the compression.
     */
    public Uni<Void> compressFiles(String[] filesPaths, Path outputZipPath) {
        return Uni.createFrom().voidPromise(promise -> {
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(outputZipPath))) {
                for (String path : filesPaths) {
                    Path fileToCompress = Paths.get(path);
                    try (InputStream is = Files.newInputStream(fileToCompress)) {
                        ZipEntry zipEntry = new ZipEntry(fileToCompress.getFileName().toString());
                        zos.putNextEntry(zipEntry);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) >= 0) {
                            zos.write(buffer, 0, length);
                        }
                        zos.closeEntry();
                    } catch (IOException e) {
                        promise.fail(e);
                        return;
                    }
                }
                promise.complete(null);
            } catch (IOException e) {
                promise.fail(e);
            }
        });
    }

    /**
     * Decompress a zip file to a specified directory.
     *
     * @param zipFilePath Path to the zip file to decompress.
     * @param outputDirPath Path to the output directory.
     * @return A Uni signal indication of the completion of the decompression.
     */
    public Uni<Void> decompressZip(Path zipFilePath, Path outputDirPath) {
        return Uni.createFrom().voidPromise(promise -> {
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {
                ZipEntry zipEntry;
                while ((zipEntry = zis.getNextEntry()) != null) {
                    Path outputPath = outputDirPath.resolve(zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(outputPath);
                    } else {
                        try (OutputStream os = Files.newOutputStream(outputPath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zis.read(buffer)) >= 0) {
                                os.write(buffer, 0, length);
                            }
                        }
                    }
                    zis.closeEntry();
                }
                promise.complete(null);
            } catch (IOException e) {
                promise.fail(e);
            }
        });
    }
}
