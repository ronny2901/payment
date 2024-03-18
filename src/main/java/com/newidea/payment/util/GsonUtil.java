package com.newidea.payment.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object objeto) {
        return gson.toJson(objeto);
    }

    public static <T> T fromJson(String json, Class<T> classe) {
        return gson.fromJson(json, classe);
    }
}