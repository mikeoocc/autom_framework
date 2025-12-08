package com.ecei.proyecto.manager;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    // Guarda el .env del proyecto en una variable.
    private static final Dotenv DOTENV = Dotenv.configure().directory(".").load();

    // Busca la variable en el .env
    public static String get(String key) {
        String value = DOTENV.get(key);
        return (value != null && !value.isBlank()) ? value : value;
    }

}