package com.shortcut.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.shortcut.shortener.common.TestParent;
import com.shortcut.shortener.domains.UserDetails;
import com.shortcut.shortener.exceptions.ShortenerException;
import com.shortcut.shortener.repositories.CommonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

/**
 * Test class for Shortener Service which handles user registration
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */

@ExtendWith(MockitoExtension.class)
class LoginServiceTest extends TestParent {

    @InjectMocks
    private LoginService loginService;

    @Mock
    CommonRepository mockCommonRepository;

    @Mock
    DocumentSnapshot mockDocumentSnapshot;

    /**
     * Test method to load user by user name which is used for login
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void loadUserByUsernameTest() throws ExecutionException, InterruptedException {

        UserDetails userDetails = getUserDetails();
        mockFirestoreInvokes(true);
        Mockito.when(mockDocumentSnapshot.toObject(Mockito.any())).thenReturn(userDetails);
        org.springframework.security.core.userdetails.UserDetails loginDetails = loginService.loadUserByUsername(userDetails.getEmail());
        Assertions.assertEquals(loginDetails.getPassword(), userDetails.getPassword());
    }

    /**
     * Test method to check failed login with unknown user name
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void loadUserByUsernameFailedTest() throws ExecutionException, InterruptedException {

        mockFirestoreInvokes(false);
        org.springframework.security.core.userdetails.UserDetails loginDetails = loginService.loadUserByUsername(getUserDetails().getEmail());
        Assertions.assertNull(loginDetails.getPassword());
        Assertions.assertFalse(loginDetails.isEnabled());
    }

    /**
     * Test method checks for custom exception to be thrown
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void loadUserByUsernameExceptionTest() throws  ExecutionException, InterruptedException{
        String email = getUserDetails().getEmail();
        Mockito.when(mockCommonRepository.getDocument(Mockito.anyString(), Mockito.anyString())).thenThrow(InterruptedException.class);
        ShortenerException thrown = Assertions.assertThrows(ShortenerException.class, () -> loginService.loadUserByUsername(email));
    }

    private void mockFirestoreInvokes(boolean t) throws InterruptedException, ExecutionException {
        Mockito.when(mockCommonRepository.getDocument(Mockito.anyString(), Mockito.anyString())).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.exists()).thenReturn(t);
    }
}
