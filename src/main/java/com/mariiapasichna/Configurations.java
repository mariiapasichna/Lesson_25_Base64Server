package com.mariiapasichna;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configurations {

    public static SessionFactory getConfigurations() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        Properties properties = getPropertiesFile();
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        configuration.getProperties().setProperty("hibernate.connection.password", password);
        configuration.getProperties().setProperty("hibernate.connection.username", username);
        return configuration.buildSessionFactory();
    }

    private static Properties getPropertiesFile() {
        Properties properties = new Properties();
        File file = new File("/Users/user/myproject.properties");
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}