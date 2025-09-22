// 代码生成时间: 2025-09-22 13:35:49
 * and has proper documentation and comments for maintainability and extensibility.
 */

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Random;

@QuarkusMain
public class TestDataGenerator implements QuarkusApplication {

    // Generates a random integer between min and max
    public int generateRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    // Generates a random string of specified length
    public String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomChar = random.nextInt(Character.MAX_VALUE);
            sb.append((char) randomChar);
        }
        return sb.toString();
    }

    // Main method to generate and print test data
    @Override
    public int run(String... args) {
        try {
            // Generate and print random integer
            int randomInt = generateRandomInt(1, 100);
            System.out.println("Random Integer: " + randomInt);

            // Generate and print random string with length 10
            String randomString = generateRandomString(10);
            System.out.println("Random String: " + randomString);

            return 0;
        } catch (Exception e) {
            System.err.println("Error generating test data: " + e.getMessage());
            return 1;
        }
    }

    // Main method for standalone execution
    public static void main(String[] args) {
        TestDataGenerator generator = new TestDataGenerator();
        generator.run(args);
    }
}