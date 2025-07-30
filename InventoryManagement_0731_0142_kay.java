// 代码生成时间: 2025-07-31 01:42:14
package com.example.inventory;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/inventory")
@QuarkusMain
public class InventoryManagement {

    // A simple in-memory storage for inventory items.
    private Map<String, Integer> inventory = new HashMap<>();

    // Adds an inventory item with a given quantity.
    @POST
    @Path("/add/{itemId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addItem(@PathParam("itemId") String itemId, String quantity) {
        try {
            int q = Integer.parseInt(quantity);
            inventory.put(itemId, inventory.getOrDefault(itemId, 0) + q);
            return Response.status(Response.Status.CREATED).entity("Item added successfully").build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid quantity").build();
        }
    }

    // Retrieves the quantity of a specific inventory item.
    @GET
    @Path("/item/{itemId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getItem(@PathParam("itemId\) String itemId) {
        if (inventory.containsKey(itemId)) {
            return Response.status(Response.Status.OK).entity(String.valueOf(inventory.get(itemId))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Item not found").build();
        }
    }

    // Main method for running the Quarkus application.
    public static void main(String... args) {
        QuarkusApplication.run(InventoryManagement.class, args);
    }
}
