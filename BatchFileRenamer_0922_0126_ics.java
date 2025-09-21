// 代码生成时间: 2025-09-22 01:26:54
package com.example.filerenamer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusMain
@ApplicationScoped
public class BatchFileRenamer implements QuarkusApplication {

    @Inject
    FileService fileService;

    @Override
    public int run(String... args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: BatchFileRenamer <directory> <newPrefix> <optionalExtension>");
            return 1;
        }

        String directoryPath = args[0];
        String newPrefix = args[1];
        String extension = args.length > 2 ? args[2] : null;

        try {
            List<File> files = fileService.getFiles(directoryPath);
            for (File file : files) {
                fileService.renameFile(file, newPrefix, extension);
            }
            System.out.println("Files have been renamed successfully.");
        } catch (IOException e) {
            System.err.println("Error renaming files: " + e.getMessage());
            return 1;
        }

        return 0;
    }
}

/*
 * FileService.java
 * A service class to handle file operations.
 */
package com.example.filerenamer;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileService {

    public List<File> getFiles(String directoryPath) throws IOException {
        Path dirPath = Paths.get(directoryPath);
        if (!Files.isDirectory(dirPath)) {
            throw new IOException("The provided path is not a directory: " + directoryPath);
        }
        return Files.list(dirPath)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    public void renameFile(File file, String newPrefix, String extension) throws IOException {
        if (file == null || !file.exists()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }
        String newFileName = newPrefix + "_" + System.nanoTime() + (extension != null ? "." + extension : "");
        Path targetPath = file.toPath().resolveSibling(newFileName);
        Files.move(file.toPath(), targetPath);
    }
}
