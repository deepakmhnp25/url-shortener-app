package com.shortcut.shortener.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShortenerException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ShortenerException.class);

    public ShortenerException(String message) {
        super(message);
        logger.error("Failed to fetch user details {}", message);
    }
}
