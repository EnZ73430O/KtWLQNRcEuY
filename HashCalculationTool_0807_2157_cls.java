// 代码生成时间: 2025-08-07 21:57:52
 * A utility class for calculating hash values of strings.
 * 
 * @author YourName
 * @version 1.0
 */
package com.example.hashtool;

import javax.enterprise.context.ApplicationScoped;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * A class to calculate hash values of strings.
 */
@ApplicationScoped
public class HashCalculationTool {

    /**
     * Calculates the hash value of a given string using the specified algorithm.
     * 
     * @param input The string to calculate the hash for.
     * @param algorithm The hashing algorithm to use.
     * @return The hash value of the input string.
     */
    public String calculateHash(String input, String algorithm) {
        try {
            // Get a MessageDigest instance for the specified algorithm
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            // Update the digest using the specified input string
            digest.update(input.getBytes(StandardCharsets.UTF_8));

            // Calculate the hash
            byte[] hashBytes = digest.digest();

            // Convert the hash bytes to a hex string
            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the calculated hash value
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the case where the specified algorithm is not available
            throw new RuntimeException("Hash algorithm not found: " + algorithm, e);
        }
    }
}
