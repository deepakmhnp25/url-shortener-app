package com.org.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.org.shortener.repositories.CommonRepository;
import com.org.shortener.constants.ApplicationConstants;
import com.org.shortener.domains.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * User service which register the user to the application.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CommonRepository commonRepository;

    /**
     * This method check if the user already exists. register the user if not exist.
     *
     * @param userDetails
     * @return boolean value indicates user regsitered or not
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean registerUser(UserDetails userDetails) {
        try {
            // Check the db if there is a user exists for the same email id
            DocumentSnapshot document = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());

            // Register user only if the user not exist
            if (!document.exists()) {
                userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
                commonRepository.createDocument(userDetails, ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());
                logger.info("User registered successfully for email {}", userDetails.getEmail());
                return Boolean.TRUE;
            }
            // return false when user already exists
            return Boolean.FALSE;
        } catch (Exception exception) {
            logger.error("Unexpected error occured. Please see the detalis {}", exception.getMessage());
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }
}
