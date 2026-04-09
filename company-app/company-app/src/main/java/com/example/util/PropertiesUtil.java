package com.example.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static final Properties props = new Properties();

    static {
        try {
            props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load application.properties");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}