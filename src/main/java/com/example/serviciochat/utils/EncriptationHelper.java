package com.example.serviciochat.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EncriptationHelper {
    private final EncriptationProperties encriptationProperties;

    @Autowired
    public EncriptationHelper(EncriptationProperties encriptationProperties) {
        this.encriptationProperties = encriptationProperties;
    }

    public String encriptarAES(String inputText) {
        try {

            byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(encriptationProperties.getAesKey().getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(encriptationProperties.getAesIv().getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            encryptedText = URLEncoder.encode(encryptedText, StandardCharsets.UTF_8.name()).replace("%", "-").trim();

            if (encryptedText.endsWith("3D-3D")) {
                encryptedText = encryptedText.substring(0, encryptedText.length() - 6) + "-3d-3d";
            }

            return encryptedText;
        } catch (Exception e) {
            return "";
        }
    }

    public String desencriptarAES(String inputText) {
        try {
            inputText = inputText.replace("-", "%");
            inputText = URLDecoder.decode(inputText, StandardCharsets.UTF_8.name());


            byte[] inputBytes = Base64.getDecoder().decode(inputText);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(encriptationProperties.getAesKey().getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(encriptationProperties.getAesIv().getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] decryptedBytes = cipher.doFinal(inputBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
