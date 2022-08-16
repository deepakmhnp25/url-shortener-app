package com.org.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.org.shortener.common.TestParent;
import com.org.shortener.domains.UserDetails;
import com.org.shortener.repositories.CommonRepository;
import com.org.shortener.constants.ApplicationConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.ExecutionException;

/**
 * Test class for User service which handles user registration
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends TestParent {

    @InjectMocks
    private UserService userService;

    @Mock
    private CommonRepository mockCommonRepository;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Register user if the user is not already registered.
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void registerUserIfNotExistsTest() throws ExecutionException, InterruptedException {

        UserDetails userDetails = getUserDetails();
        mockFirestoreCall(Boolean.FALSE);
        // assert user created
        Assertions.assertTrue(userService.registerUser(userDetails));
        // verify user details creation db transaction happened or not
        Mockito.verify(mockCommonRepository, Mockito.times(1))
                .createDocument(userDetails, ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());
    }

    /**
     * Do not register the user if the user already exists
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void doNotRegisterUserIfExistsTest() throws ExecutionException, InterruptedException {

        UserDetails userDetails = getUserDetails();
        mockFirestoreCall(Boolean.TRUE);
        // assert user not created
        Assertions.assertFalse(userService.registerUser(userDetails));
        // verify no db transaction happened.
        Mockito.verify(mockCommonRepository, Mockito.times(0))
                .createDocument(userDetails, ApplicationConstants.COLLECTION_SHORTENER_USERS, userDetails.getEmail());
    }

    /**
     * User registration fails when an exception happens
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void exceptionWhileUserRegistrationTest() throws ExecutionException, InterruptedException {
        Mockito.when(mockCommonRepository.getDocument(Mockito.anyString(), Mockito.anyString())).thenThrow(InterruptedException.class);
        Assertions.assertFalse(userService.registerUser(getUserDetails()));
    }

    private void mockFirestoreCall(Boolean userExists) throws InterruptedException, ExecutionException {
        Mockito.when(mockCommonRepository.getDocument(Mockito.anyString(), Mockito.anyString())).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(userExists);
    }

}