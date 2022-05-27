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

@Service
public class ShortenerService {

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    Environment environment;

    private static Logger logger = LoggerFactory.getLogger(ShortenerService.class);

    public UrlDetails shortenUrl(UrlDetails urlDetails){
        String shortId = RandomStringUtils.random(ApplicationConstants.SHORT_ID_LENGTH, ApplicationConstants.SHORT_ID_PATTERN );
        String shortenedUrl = new StringBuilder(ApplicationConstants.BASE_URL)
                .append(environment.getProperty(ApplicationConstants.LOCAL_SERVER_PORT)).append("/").append(shortId).toString();
        urlDetails.setShortenedUrl(shortenedUrl);
        commonRepository.createDocument(urlDetails,
                ApplicationConstants.COLLECTION_SHORTENER_URLS, shortId);
        logger.info("Url shortened successfully {}", shortenedUrl);
        return urlDetails;
    }

    public UrlDetails getOriginalUrl(String shortId) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = commonRepository.getDocument(ApplicationConstants.COLLECTION_SHORTENER_URLS, shortId);
        return document.toObject(UrlDetails.class);
    }
}
