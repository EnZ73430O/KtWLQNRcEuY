// 代码生成时间: 2025-09-19 02:53:54
package com.example.auth;

import io.quarkus.security.AuthenticationException;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.spi.AuthenticationMechanism;
import java.util.Set;

public class AuthenticationService implements AuthenticationMechanism {

    // This method checks if the provided credentials are valid.
    @Override
    public SecurityIdentity authenticate(Credential credential) throws AuthenticationException {
        // Assuming credential contains a username and password
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upCredential = (UsernamePasswordCredential) credential;
            String username = upCredential.getUsername();
            char[] password = upCredential.getPassword();

            // Here you would normally interact with a database or some other service to check credentials.
            // For simplicity, we're using hardcoded values.
            if ("admin".equals(username) && "password".equals(new String(password))) {
                return createSecurityIdentity(username);
            } else {
                throw new AuthenticationFailedException("Invalid username or password");
            }
        }
        return null;
    }

    // Helper method to create a SecurityIdentity from a username
    private SecurityIdentity createSecurityIdentity(String username) {
        // Create a simple security identity with the authenticated user's role
        return SecurityIdentity.builder()
                .setPrincipal(username)
                .addRole("user")
                .build();
    }

    // This method returns the names of the credentials this mechanism can authenticate.
    @Override
    public Set<String> getCredentialTypes() {
        return Set.of(UsernamePasswordCredential.class.getName());
    }
}

/*
 * UsernamePasswordCredential.java
 * 
 * A simple class to hold username and password credentials.
 */
package com.example.auth;

import io.quarkus.security.credential.PasswordCredential;

public class UsernamePasswordCredential implements PasswordCredential {

    private final String username;
    private final char[] password;

    public UsernamePasswordCredential(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public char[] getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
