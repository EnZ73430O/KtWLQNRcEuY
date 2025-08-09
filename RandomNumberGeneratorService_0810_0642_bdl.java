// 代码生成时间: 2025-08-10 06:42:39
import javax.inject.Singleton;
# 添加错误处理
import java.util.concurrent.ThreadLocalRandom;
# 改进用户体验

/**
# FIXME: 处理边界情况
 * RandomNumberGeneratorService class provides functionality to generate a random number within a specified range.
# FIXME: 处理边界情况
 */
@Singleton
public class RandomNumberGeneratorService {

    /**
     * Generates a random number between the given range.
     * 
# 优化算法效率
     * @param min the minimum value of the range (inclusive)
     * @param max the maximum value of the range (exclusive)
# NOTE: 重要实现细节
     * @return a random number between min and max
     * @throws IllegalArgumentException if min is greater than max or if the values are negative
     */
    public int generateRandomNumber(int min, int max) {
        // Check if the range is valid
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        if (min < 0 || max < 0) {
            throw new IllegalArgumentException("Minimum and maximum values cannot be negative.");
        }

        // Generate a random number within the specified range
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
