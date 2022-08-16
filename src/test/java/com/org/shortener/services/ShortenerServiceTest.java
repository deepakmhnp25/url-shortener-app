package com.org.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.org.shortener.common.TestParent;
import com.org.shortener.repositories.CommonRepository;
import com.org.shortener.constants.ApplicationConstants;
import com.org.shortener.domains.UrlDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutionException;

/**
 * Test class for Shortener Service which handles user registration
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@ExtendWith(MockitoExtension.class)
class ShortenerServiceTest extends TestParent {

    @InjectMocks
    private ShortenerService shortenerService;

    @Mock
    private Environment environment;

    @Mock
    private CommonRepository mockCommonRepository;

    @Mock
    private DocumentSnapshot mockDocumentSnapshot;

    /**
     * This unit test method checks whether the url shortened or not
     * it also checks if a database transaction to store it triggered or not
     */
    @Test
    void shortenUrlTest(){
        // making the shortened url null. in later stages we calculate it and assert
        UrlDetails urlDetails = getUrlDetails();
        urlDetails.setShortenedUrl(null);
        // assert if there is a shortenedUrl created
        Assertions.assertNotNull(shortenerService.shortenUrl(urlDetails).getShortenedUrl());
        // verify db transaction is made to store it or not
        Mockito.verify(mockCommonRepository, Mockito.times(1))
                .createDocument(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    /**
     * * This unit test method checks for loading original url from
     * a shortened url.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void getOriginalUrlTest() throws ExecutionException, InterruptedException {

        Mockito.when(mockCommonRepository.getDocument(Mockito.anyString(), Mockito.anyString())).thenReturn(mockDocumentSnapshot);
        Mockito.when(mockDocumentSnapshot.toObject(Mockito.any())).thenReturn(getUrlDetails());
        UrlDetails urlDetails = shortenerService.getOriginalUrl(ApplicationConstants.BASE_URL+"/adb39kko");
        Assertions.assertNotNull(urlDetails);
        Assertions.assertEquals(ApplicationConstants.BASE_URL, urlDetails.getUrl());
    }
}
