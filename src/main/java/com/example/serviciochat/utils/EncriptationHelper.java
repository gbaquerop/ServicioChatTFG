package com.example.serviciochat.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class EncriptationHelper {
    // AES encryption key (128-bit key = 16 bytes)
    private static final String KEY = "gbaquerosecrets_"; // Ensure length is 16 bytes
    private static final String IV = "rpaSPvIvVLlrcmtz";  // IV must also be 16 bytes

    public static String encriptarAES(String inputText) {
        try {

            byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);

            // Initialize AES cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // Encrypt the input text
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            // Convert encrypted bytes to Base64
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            // URL encode and replace special characters
            encryptedText = URLEncoder.encode(encryptedText, StandardCharsets.UTF_8.name()).replace("%", "-").trim();

            if (encryptedText.endsWith("3D-3D")) {
                encryptedText = encryptedText.substring(0, encryptedText.length() - 6) + "-3d-3d";
            }

            return encryptedText;
        } catch (Exception e) {
            return "";
        }
    }

    public static String desencriptarAES(String inputText) {
        try {
            // Replace "-" with "%" and URL decode
            inputText = inputText.replace("-", "%");
            inputText = URLDecoder.decode(inputText, StandardCharsets.UTF_8.name());


            // Convert Base64 string to bytes
            byte[] inputBytes = Base64.getDecoder().decode(inputText);

            // Initialize AES cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // Decrypt the input text
            byte[] decryptedBytes = cipher.doFinal(inputBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
