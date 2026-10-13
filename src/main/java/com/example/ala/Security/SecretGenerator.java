package com.example.ala.Security;


import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {
    public static void main(String[] args) {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        System.out.println(Base64.getEncoder().encodeToString(bytes));
    }
}

