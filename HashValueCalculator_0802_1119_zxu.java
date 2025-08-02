// 代码生成时间: 2025-08-02 11:19:37
package com.example.hashcalculator;

import javax.enterprise.context.ApplicationScoped;
# NOTE: 重要实现细节
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * HashValueCalculator class to provide hash calculation functionality.
 */
@ApplicationScoped
public class HashValueCalculator {

    // Generates a hash value for the given input string
    public String calculateHash(String input) throws NoSuchAlgorithmException {
        // Check if the input is null or empty
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }

        // Get the message digest instance for SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Compute the hash of the input string
        byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
# 优化算法效率

        // Encode the hash bytes to a Base64 string
        String hashValue = Base64.getEncoder().encodeToString(hashBytes);

        return hashValue;
    }
}
# 扩展功能模块
