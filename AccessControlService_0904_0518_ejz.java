// 代码生成时间: 2025-09-04 05:18:34
package com.example.accesscontrol;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.UnauthorizedException;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Service class for handling access control in a Quarkus application.
 */
@ApplicationScoped
public class AccessControlService {

    private static final String[] ADMIN_PERMISSIONS = {"admin:read", "admin:write"};
    private static final String[] USER_PERMISSIONS = {"user:read"};

    /**
     * Checks if the current user has the required permissions.
     *
     * @param requiredPermissions The permissions required to access the resource.
     * @param securityContext The security context containing the user's principal.
     * @throws UnauthorizedException If the user is not authenticated.
     * @throws ForbiddenException If the user does not have the required permissions.
     */
    public void checkPermissions(String[] requiredPermissions, SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();

        // Check if the user is authenticated
        if (principal == null) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        // Check if the user has the required permissions
        String role = getUserRole(principal);
        for (String requiredPermission : requiredPermissions) {
            if (!hasPermission(role, requiredPermission)) {
                throw new ForbiddenException("User does not have the required permissions.");
            }
        }
    }

    /**
     * Determines the user's role based on their principal name.
     *
     * @param principal The user's principal.
     * @return The user's role as a string.
     */
    private String getUserRole(Principal principal) {
        // This is a simple implementation that assumes the principal's name is in the format 'role:name'
        // and extracts the role. In a real application, this would be more complex and involve
        // checking against a user database or identity provider.
        return principal.getName().split(":")[0];
    }

    /**
     * Checks if the user has a specific permission based on their role.
     *
     * @param role The user's role.
     * @param permission The permission to check for.
     * @return True if the user has the permission, false otherwise.
     */
    private boolean hasPermission(String role, String permission) {
        switch (role) {
            case "admin":
                for (String adminPermission : ADMIN_PERMISSIONS) {
                    if (adminPermission.equals(permission)) {
                        return true;
                    }
                }
                break;
            case "user":
                for (String userPermission : USER_PERMISSIONS) {
                    if (userPermission.equals(permission)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }
}
