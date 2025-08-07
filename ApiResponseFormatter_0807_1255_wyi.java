// 代码生成时间: 2025-08-07 12:55:25
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.HashMap;

/**
 * ApiResponseFormatter is a RESTful service that provides API response formatting functionality.
 */
@Path("/api")
public class ApiResponseFormatter {

    // Define the endpoint for formatting API responses
    @POST
    @Path("/format")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Format API Response", description = "Formats the API response into a structured JSON format.")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ApiResponse.class)))
    public Uni<Response> formatApiResponse(Map<String, Object> payload) {
        try {
            // Validate the payload
            if (payload == null || payload.isEmpty()) {
                throw new IllegalArgumentException("Payload cannot be null or empty.");
            }

            // Create a structured API response
            ApiResponse response = new ApiResponse(
                "success",
                "API response has been formatted successfully.",
                payload
            );

            // Return the formatted API response
            return Uni.createFrom().item(response)
                .onItem().transform(res -> Response.ok(res).build());
        } catch (IllegalArgumentException e) {
            // Handle payload validation errors
            ApiResponse errorResponse = new ApiResponse(
                "error",
                e.getMessage(),
                null
            );
            return Uni.createFrom().item(errorResponse)
                .onItem().transform(res -> Response.status(Response.Status.BAD_REQUEST).entity(res).build());
        }
    }

    /**
     * ApiResponse is a simple data transfer object to represent API responses.
     */
    public static class ApiResponse {
        private String status;
        private String message;
        private Map<String, Object> data;

        public ApiResponse(String status, String message, Map<String, Object> data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters and setters for the fields
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }
}