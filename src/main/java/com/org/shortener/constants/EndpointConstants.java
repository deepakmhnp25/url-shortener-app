package com.org.shortener.constants;

/**
 * This class holds all the url end points in the whole application
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
public class EndpointConstants {

    private EndpointConstants() {
    }

    public static final String URL_SHORTENER = "/urlshortener";
    public static final String SHORTEN = "/shorten";
    public static final String HOME = "/";
    public static final String REGISTER = "/register";
    public static final String REDIRECT_SHORTENED_URL = "/{urlHash}";
}
