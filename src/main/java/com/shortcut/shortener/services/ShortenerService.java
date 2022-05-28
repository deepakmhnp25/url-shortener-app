package com.shortcut.shortener.services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.domains.UrlDetails;
import com.shortcut.shortener.repositories.CommonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;


/**
 * This service handles shortening the input url and
 * fetching the original url from db using hash.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Service
public class ShortenerService {

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    Environment environment;

    private static Logger logger = LoggerFactory.getLogger(ShortenerService.class);

    /**
     * this method shorten the input url and returns the shortened url
     *
     * @param urlDetails input url
     * @return returns original and shortened url
     */
    public UrlDetails shortenUrl(UrlDetails urlDetails){

        // Generate a hash key for the url
        String shortId = RandomStringUtils.random(ApplicationConstants.SHORT_ID_LENGTH, ApplicationConstants.SHORT_ID_PATTERN );
        // Generate the url and store it in the db.
        String shortenedUrl = new StringBuilder(ApplicationConstants.BASE_URL)
                .append(environment.getProperty(ApplicationConstants.LOCAL_SERVER_PORT)).append("/").append(shortId).toString();
        urlDetails.setShortenedUrl(shortenedUrl);
        commonRepository.createDocument(urlDetails,
                ApplicationConstants.COLLECTION_SHORTENER_URLS, shortId);
        logger.info("Url shortened successfully {}", shortenedUrl);
        return urlDetails;
    }

    /**
     * Get the original url using the shortened url.
     *
     * @param shortId hashkey corresponding to the url.
     * @return url details with original and short url
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public UrlDetails getOriginalUrl(String shortId) throws ExecutionException, InterruptedException {
        // Fetch the original url from the db using hashkey
        DocumentSnapshot document = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_URLS, shortId);
        return document.toObject(UrlDetails.class);
    }
}
