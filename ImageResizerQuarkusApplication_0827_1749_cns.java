// 代码生成时间: 2025-08-27 17:49:09
package com.example.imageresizer;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main entry point for the Image Resizer application.
 */
@QuarkusMain
public class ImageResizerQuarkusApplication implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        // Check if input arguments are provided
        if (args.length < 3) {
            System.out.println("Usage: ImageResizerQuarkusApplication <source directory> <destination directory> <width> <height>");
            return 1;
        }

        String sourceDir = args[0];
        String destinationDir = args[1];
        int targetWidth = Integer.parseInt(args[2]);
        int targetHeight = Integer.parseInt(args[3]);

        try {
            resizeImagesInDirectory(sourceDir, destinationDir, targetWidth, targetHeight);
        } catch (IOException e) {
            System.err.println("Error processing images: " + e.getMessage());
            return 1;
        }

        return 0;
    }

    /**
     * Resizes all images in the given source directory and saves them to the destination directory.
     *
     * @param sourceDir      the directory containing the original images.
     * @param destinationDir the directory where resized images will be saved.
     * @param targetWidth    the target width of the resized images.
     * @param targetHeight   the target height of the resized images.
     * @throws IOException if an I/O error occurs during processing.
     */
    private void resizeImagesInDirectory(String sourceDir, String destinationDir, int targetWidth, int targetHeight) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path destinationPath = Paths.get(destinationDir);

        if (!Files.exists(sourcePath) || !Files.isDirectory(sourcePath)) {
            throw new IllegalArgumentException("Source directory does not exist or is not a directory: " + sourceDir);
        }

        if (!Files.exists(destinationPath) && !Files.createDirectories(destinationPath)) {
            throw new IllegalArgumentException("Failed to create destination directory: " + destinationDir);
        }

        List<File> imageFiles = Files.walk(sourcePath)
                .filter(p -> p.toString().endsWith(".jpg") || p.toString().endsWith(".png"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File imageFile : imageFiles) {
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                System.err.println("Skipping non-image file: " + imageFile.getName());
                continue;
            }

            resizeImage(imageFile, originalImage, targetWidth, targetHeight, destinationPath);
        }
    }

    /**
     * Resizes a single image and saves it to the destination directory.
     *
     * @param originalFile    the original image file.
     * @param image          the image to resize.
     * @param targetWidth    the target width of the resized image.
     * @param targetHeight   the target height of the resized image.
     * @param destinationDir the directory where the resized image will be saved.     * @throws IOException if an I/O error occurs during processing.
     */
    private void resizeImage(File originalFile, BufferedImage image, int targetWidth, int targetHeight, Path destinationDir) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(image.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);

        String fileName = originalFile.getName();
        String destinationFilePath = destinationDir.resolve(fileName).toString();

        File destinationFile = new File(destinationFilePath);
        ImageIO.write(resizedImage, "png", destinationFile);
    }

    /**
     * Main method for testing the application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        ImageResizerQuarkusApplication app = new ImageResizerQuarkusApplication();
        int result = app.run(args);
        System.exit(result);
    }
}
