package com.shortcut.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.shortcut.shortener.repositories.CommonRepository;
import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.domains.LoginDetails;
import com.shortcut.shortener.exceptions.ShortenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Login service which authenticates the user.
 */
@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private CommonRepository commonRepository;

    /**
     * This method loads the user and creates the user details which is required for spring security authentication
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            DocumentSnapshot document = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_USERS, email);
            return authenticate(document);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ShortenerException(e.getMessage());
        }
    }

    /**
     * This maps the user details from db.
     * @param document
     * @return LoginDetails
     */
    private LoginDetails authenticate(DocumentSnapshot document) {
        if(document.exists()){
            com.shortcut.shortener.domains.UserDetails userDetails
                    = document.toObject(com.shortcut.shortener.domains.UserDetails.class);
            return new LoginDetails(userDetails.getEmail(), userDetails.getPassword(), Boolean.TRUE,
                    Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
        } else {
            return new LoginDetails(null, null, Boolean.TRUE,
                    Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        }
    }
}
