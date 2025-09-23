// 代码生成时间: 2025-09-23 11:54:54
package com.example.services;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A service for generating random numbers.
 * This service is designed to be stateless and can be used across multiple threads.
 */
@ApplicationScoped
public class RandomNumberGeneratorService {

    /**
     * Generates a random number between the specified range.
     *
     * @param min The minimum value of the range (inclusive).
     * @param max The maximum value of the range (exclusive).
     * @return A random number within the specified range.
     * @throws IllegalArgumentException If the minimum value is greater than the maximum value.
     */
    public int generateRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than or equal to the maximum value.");
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
