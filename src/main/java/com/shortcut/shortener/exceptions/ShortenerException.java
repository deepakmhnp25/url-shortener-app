package com.shortcut.shortener.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception class for handling Runtime exceptions
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
public class ShortenerException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ShortenerException.class);

    /**
     * Constructor for custom exception
     *
     * @param message
     */
    public ShortenerException(String message) {
        super(message);
        logger.error("Failed to fetch user details {}", message);
    }
}
