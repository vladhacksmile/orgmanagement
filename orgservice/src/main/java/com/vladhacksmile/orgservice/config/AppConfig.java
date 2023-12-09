package com.vladhacksmile.orgservice.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppConfig {

    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);
                replaceEnvVariables();
            } else {
                System.out.println("Warning: " + PROPERTIES_FILE + " not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceEnvVariables() {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value != null) {
                Matcher matcher = Pattern.compile("\\$\\{([^}]+)}").matcher(value);

                StringBuffer replacedValue = new StringBuffer();
                while (matcher.find()) {
                    String envVarName = matcher.group(1);
                    // Получаем значение переменной окружения
                    String envVarValue = System.getenv(envVarName);
                    // Если переменная окружения не найдена, используем значение из системных свойств
                    matcher.appendReplacement(replacedValue, envVarValue != null ? Matcher.quoteReplacement(envVarValue) : "");
                }
                matcher.appendTail(replacedValue);

                properties.setProperty(key, replacedValue.toString());
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
