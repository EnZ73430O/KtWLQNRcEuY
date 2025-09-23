// 代码生成时间: 2025-09-24 00:55:29
package com.yourcompany.userpermissionservice;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

// The User class represents the user entity with permissions
class User {
    private String username;
    private Set<String> permissions;

    public User(String username) {
        this.username = username;
        this.permissions = new HashSet<>();
    }

    public void addPermission(String permission) {
        permissions.add(permission);
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}

// The UserPermissionService class provides user permission management
class UserPermissionService {
    private User user;

    public UserPermissionService(User user) {
        this.user = user;
    }

    public void grantPermission(String permission) {
        user.addPermission(permission);
        System.out.println("Permission granted: " + permission);
    }

    public void revokePermission(String permission) {
        user.getPermissions().remove(permission);
        System.out.println("Permission revoked: " + permission);
    }

    public boolean checkPermission(String permission) {
        return user.hasPermission(permission);
    }
}

// RESTful service for managing user permissions
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user/{username}/permissions")
public class PermissionResource {
    private UserPermissionService permissionService;

    public PermissionResource() {
        // Initialize with a default user
        this.permissionService = new UserPermissionService(new User("defaultUser"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPermissions(@PathParam("username") String username) {
        // TODO: Handle different user instances
        // For simplicity, we use the same user instance for all requests
        return Response.ok(permissionService.getPermissions()).build();
    }

    @PUT
    @Path("/{permission}")
    public Response grantPermission(@PathParam("username") String username, @PathParam("permission") String permission) {
        permissionService.grantPermission(permission);
        return Response.ok().build();
    }

    @PUT
    @Path("/revoke/{permission}")
    public Response revokePermission(@PathParam("username\) String username, @PathParam("permission") String permission) {
        permissionService.revokePermission(permission);
        return Response.ok().build();
    }
}

// The main application class
@QuarkusMain
@ApplicationPath("/")
public class UserPermissionApplication extends Application {
    // Define REST services here
}
