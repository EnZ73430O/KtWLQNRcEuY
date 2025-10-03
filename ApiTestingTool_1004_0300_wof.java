// 代码生成时间: 2025-10-04 03:00:21
 * It allows users to test various API endpoints with different HTTP methods.
 * 
 * @author Your Name
 * @version 1.0
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import io.smallrye.mutiny.Uni;
import java.net.URI;
import java.util.Map;

@Path("/apitest")
public class ApiTestingTool {

    @GET
    @Path("/get")
    public Response testGet(@QueryParam("url") String url) {
        return sendRequest("GET", url, null);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testPost(@QueryParam("url") String url, @QueryParam("data") String data) {
        return sendRequest("POST", url, data);
    }

    // Other HTTP methods can be added similarly

    private Response sendRequest(String method, String url, String data) {
        try {
            URI uri = new URI(url);
            RestClient client = RestClientBuilder.newBuilder().baseUri(uri).build();
            Response response;

            switch (method.toUpperCase()) {
                case "GET":
                    response = client.target(url).request().get();
                    break;
                case "POST":
                    response = client.target(url).request().post(Entity.entity(data, MediaType.APPLICATION_JSON));
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST)
                                    .entity("Unsupported HTTP method")
                                    .build();
            }

            return response;
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error while sending request")
                        .build();
        }
    }
}
