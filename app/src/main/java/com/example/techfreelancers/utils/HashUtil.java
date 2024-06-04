package com.example.techfreelancers.utils;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

public class HashUtil {

    private static final String DEFAULT_HASH_SALT = "iMWEyIsqFBJ+PFoSErec/Q==";

    /**
     * Generate a random salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Hash the password with the salt
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password, Context context) {
        // load hash salt from config file
        Properties config = ConfigUtil.loadConfig(context);
        String salt = null;
        if (config != null) {
            salt = config.getProperty("hashsalt");
        } else {
            salt = DEFAULT_HASH_SALT;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
