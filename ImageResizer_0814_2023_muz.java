// 代码生成时间: 2025-08-14 20:23:18
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/image-resizer")
public class ImageResizer {

    @POST
    @Consumes(MediaType APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response resizeImages(InputStream inputStream) throws IOException {
        // Deserialize the request body into a list of image paths and desired dimensions
        List<ImageResizeRequest> requests = deserializeRequest(inputStream);

        List<ImageResizeResponse> responses = new ArrayList<>();
        for (ImageResizeRequest request : requests) {
            responses.add(resizeImage(request));
        }

        return Response.ok(serializeResponse(responses)).build();
    }

    /*
     * Deserializes the JSON input stream into a list of ImageResizeRequest objects.
     */
    private List<ImageResizeRequest> deserializeRequest(InputStream inputStream) {
        // Implementation of deserialization logic
        // For simplicity, this is omitted and should be replaced with actual JSON deserialization code
        return new ArrayList<>();
    }

    /*
     * Serializes the list of ImageResizeResponse objects into a JSON string.
     */
    private String serializeResponse(List<ImageResizeResponse> responses) {
        // Implementation of serialization logic
        // For simplicity, this is omitted and should be replaced with actual JSON serialization code
        return new JSONArray();
    }

    /*
     * Resizes a single image to the desired dimensions.
     */
    private ImageResizeResponse resizeImage(ImageResizeRequest request) {
        File imageFile = new File(request.getImagePath());
        try {
            BufferedImage image = ImageIO.read(imageFile);
            BufferedImage resizedImage = new BufferedImage(request.getWidth(), request.getHeight(), image.getType());
            resizedImage.getGraphics().drawImage(image.getScaledInstance(request.getWidth(), request.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);

            // Save the resized image to a new file
            ImageIO.write(resizedImage, request.getFormat(), new File(request.getOutputPath()));

            return new ImageResizeResponse(request.getImagePath(), request.getWidth(), request.getHeight(), true);
        } catch (IOException e) {
            // Handle exceptions and return a failure response
            return new ImageResizeResponse(request.getImagePath(), request.getWidth(), request.getHeight(), false, e.getMessage());
        }
    }
}

/*
 * Represents a request to resize an image.
 */
class ImageResizeRequest {
    private String imagePath;
    private int width;
    private int height;
    private String format;
    private String outputPath;

    // Getters and setters omitted for brevity
}

/*
 * Represents the response from resizing an image.
 */
class ImageResizeResponse {
    private String imagePath;
    private int width;
    private int height;
    private boolean success;
    private String message;

    // Constructor, getters and setters omitted for brevity
}