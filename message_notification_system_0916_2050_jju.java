// 代码生成时间: 2025-09-16 20:50:04
package com.example.notification;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;

@Path("/notification")
public class MessageNotificationSystem {

    @Inject
    @Stream("message-sink")
    Emitter<String> emitter;

    /**
     * Posts a message to the message system for notification.
     *
     * @param message The message to be posted.
     * @return A response indicating the success or failure of the operation.
     */
    @POST
    @Path("/post-message")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postMessage(String message) {
        try {
            // Emit the message to be processed
            emitter.send(Acknowledgment.Strategy.MANUAL, message);
            return Response.status(Response.Status.CREATED).entity("Message posted successfully").build();
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to post message").build();
        }
    }

    /**
     * Retrieves the last message from the system.
     *
     * @return The last message or a message indicating the absence of messages.
     */
    @GET
    @Path("/get-last-message")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLastMessage() {
        // Implementation for retrieving the last message
        // This would typically involve querying a database or message store
        String lastMessage = "No messages available";
        return lastMessage;
    }
}
