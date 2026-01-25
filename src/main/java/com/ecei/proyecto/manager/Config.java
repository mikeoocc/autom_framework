package com.ecei.proyecto.manager;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv DOTENV = Dotenv.configure().directory(".").load();

    public static String get(String key) {
        String value = DOTENV.get(key);
        return (value != null && !value.isBlank()) ? value : value;
    }

}