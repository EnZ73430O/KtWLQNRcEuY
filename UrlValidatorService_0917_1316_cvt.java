// 代码生成时间: 2025-09-17 13:16:09
// UrlValidatorService.java

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Service for validating the URL link.
 */
@ApplicationScoped
@Path("/urlValidator")
@RegisterForReflection
public class UrlValidatorService {

    /**
     * Validates a given URL string.
     *
     * @param urlString The URL string to validate.
     * @return A response indicating whether the URL is valid.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUrl(String urlString) {
        try {
            // Attempt to create a URL object to validate the string
            new URL(urlString);
            return Response.status(Response.Status.OK).entity(
                String.format({"%s": "%s"}
                    , "valid"
                    , "true"))
                .build();
        } catch (MalformedURLException e) {
            // If the URL is invalid, return an error response
            return Response.status(Response.Status.BAD_REQUEST).entity(
                String.format({"%s": "%s"}
                    , "valid"
                    , "false"))
                .build();
        }
    }
}
