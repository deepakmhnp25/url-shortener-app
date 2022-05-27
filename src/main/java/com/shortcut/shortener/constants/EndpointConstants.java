package com.shortcut.shortener.constants;

public class EndpointConstants {

    private EndpointConstants() {
    }

    public static final String URL_SHORTENER = "/urlshortener";
    public static final String SHORTEN = "/shorten";
    public static final String LOGIN = "/login";
    public static final String HOME = "/";
    public static final String REGISTER = "/register";
    public static final String SHORTENED_URL = "/{urlHash}";
}
