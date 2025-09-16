// 代码生成时间: 2025-09-16 16:21:33
package com.example.random;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Singleton;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service to generate random numbers.
 */
@Singleton
@RegisterForReflection
public class RandomNumberGeneratorService {

    /**
     * Generates a random number between two values (inclusive).
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return A random integer between min and max (inclusive).
     */
    public int generateRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("The minimum value must be less than the maximum value.");
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
