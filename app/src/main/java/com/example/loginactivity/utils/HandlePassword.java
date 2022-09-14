package com.example.loginactivity.utils;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class HandlePassword {
    private static Cipher cipher;
    private KeyGenerator keyGenerator;
    private SecretKey secretKey;

    public HandlePassword() throws Exception {
        this.keyGenerator = KeyGenerator.getInstance("AES");
        this.keyGenerator.init(128);
        this.secretKey = keyGenerator.generateKey();
        this.cipher = Cipher.getInstance("AES");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String password) throws Exception {
        byte[] passwordBytes = password.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedPassword = cipher.doFinal(passwordBytes);
        Base64.Encoder encoder = Base64.getEncoder();
        String hashedPassword = encoder.encodeToString(encryptedPassword);
        return hashedPassword;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String password) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(password);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedPassword = new String(decryptedByte);
        return decryptedPassword;
    }
}