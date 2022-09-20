package com.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EncrytedPasswordUtils {

    // Encryte Password with BCryptPasswordEncoder
    public String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123456";
        String encrytedPassword = new EncrytedPasswordUtils().encrytePassword(password);

        System.out.println("Encryted Password: " + encrytedPassword);
        System.out.println(new BCryptPasswordEncoder().matches("1234", encrytedPassword));

        Map<String, String> map = new HashMap<>();
    }

}
