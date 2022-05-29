package com.shortcut.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.domains.LoginDetails;
import com.shortcut.shortener.exceptions.ShortenerException;
import com.shortcut.shortener.repositories.CommonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Login service which authenticates the user.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Service
public class LoginService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private CommonRepository commonRepository;

    /**
     * This method loads the user and creates the user details which is required for spring security authentication
     *
     * @param email of the user
     * @return UserDetails object
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authenticate(email);
    }

    /**
     * This metod fetches the user details from db and prepares the LoginDetails to be used with security module
     *
     * @param email of the user
     * @return LoginDetails
     */
    private LoginDetails authenticate(String email) {
        try {
            // Fetches the user details from db using emailId
            DocumentSnapshot userDocument = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_USERS, email);

            // Checks if the user exists in the db and provide the details to spring security
            if (userDocument.exists()) {
                com.shortcut.shortener.domains.UserDetails userDetails
                        = userDocument.toObject(com.shortcut.shortener.domains.UserDetails.class);
                logger.info("User found for email id {}", userDetails.getEmail());
                return new LoginDetails(userDetails.getEmail(), userDetails.getPassword(), Boolean.TRUE,
                        Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
            } else {
                // User does not exist in db. security module does the authentication decision based on this.s
                logger.warn("No user found for the email id {} ", email);
                return new LoginDetails(null, null, Boolean.TRUE,
                        Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
            }

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ShortenerException(e.getMessage());
        }
    }
}
