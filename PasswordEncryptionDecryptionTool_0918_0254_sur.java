// 代码生成时间: 2025-09-18 02:54:59
 * @author [Your Name]
 * @version 1.0
 */
package com.example.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryptionDecryptionTool {
# 改进用户体验

    private static final String ALGORITHM = "AES"; // Encryption algorithm

    // Generate a new encryption key
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256, SecureRandom.getInstanceStrong()); // 256-bit AES key
        return keyGenerator.generateKey();
    }

    // Encrypt the password
    public static String encrypt(String password, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt the password
    public static String decrypt(String encryptedPassword, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            // Generate a new key
            SecretKey key = generateKey();

            // Original password
            String password = "MySecretPassword123";

            // Encrypt the password
# NOTE: 重要实现细节
            String encryptedPassword = encrypt(password, key);
            System.out.println("Encrypted Password: " + encryptedPassword);

            // Decrypt the password
            String decryptedPassword = decrypt(encryptedPassword, key);
            System.out.println("Decrypted Password: " + decryptedPassword);

        } catch (Exception e) {
# 优化算法效率
            e.printStackTrace();
        }
    }
}
