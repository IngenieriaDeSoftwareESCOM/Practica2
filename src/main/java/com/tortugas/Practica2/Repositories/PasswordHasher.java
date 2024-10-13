package com.tortugas.Practica2.Repositories;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;

public class PasswordHasher {
    public String getHash(String text){
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Convert the input string to bytes and compute the hash
            byte[] encodedhash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            
            // Convert byte array into signum representation (positive number)
            BigInteger number = new BigInteger(1, encodedhash);
            
            // Convert the hash into a hexadecimal string
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros if necessary to make it 64 chars long
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } 
    }
}
