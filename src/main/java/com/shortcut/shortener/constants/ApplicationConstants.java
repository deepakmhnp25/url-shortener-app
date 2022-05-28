package com.shortcut.shortener.constants;

/**
 * This class holds all the application level constant values.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
public class ApplicationConstants {

    /*Utility classes, which are collections of static members, are not meant to be instantiated*/
    private ApplicationConstants() {
    }

    public static final String COLLECTION_SHORTENER_USERS = "shortener_users";
    public static final String VIEW_REGISTRATION = "registration";
    public static final int SHORT_ID_LENGTH = 8;
    public static final String SHORT_ID_PATTERN = "0123456789abcdef";
    public static final String COLLECTION_SHORTENER_URLS = "shortener_urls";
    public static final String LOCAL_SERVER_PORT = "local.server.port";
    public static final String BASE_URL = "http://localhost:";
}
