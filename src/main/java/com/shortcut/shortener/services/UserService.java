package com.shortcut.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.shortcut.shortener.repositories.CommonRepository;
import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.domains.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * User service which register the user to the application.
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
     * @return boolean
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean registerUser(UserDetails userDetails) {
        try {
            DocumentSnapshot document = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());
            /*Register user only if the user not exist*/
            if (!document.exists()) {
                userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
                commonRepository.createDocument(userDetails, ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception exception) {
            logger.error("Unexpected error occured. Please see the detalis {}", exception.getMessage());
            Thread.currentThread().interrupt();
            return Boolean.FALSE;
        }
    }
}
