package com.owen.util;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    static Properties properties = new Properties();

    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (properties == null) {
            return null;
        }
        return properties.get(key);
    }

    public static String getString(String key) {
        if (properties == null) {
            return null;
        }
        return (String) properties.get(key);
    }

    public static Integer getInt(String key) {
        if (properties == null) {
            return null;
        }
        return (Integer) properties.get(key);
    }
}
