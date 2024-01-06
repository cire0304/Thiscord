package com.example.thiscode.utils;

import jakarta.servlet.Filter;
import jakarta.servlet.http.Cookie;

public class CookieUtils {

    private static final String TOKEN = "TOKEN";

    public static Cookie createJwtCookie(String token) {
        Cookie cookie = new Cookie(TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        cookie.setSecure(true);
        return cookie;
    }

}
