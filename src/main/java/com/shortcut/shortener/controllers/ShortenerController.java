package com.shortcut.shortener.controllers;

import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.constants.EndpointConstants;
import com.shortcut.shortener.domains.UrlDetails;
import com.shortcut.shortener.services.ShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.concurrent.ExecutionException;


/**
 * Controller class where all the url shortening related actions are preformed
 * Creating a shortened url and loading a shortened url is handled here.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Controller
public class ShortenerController {

    private static Logger logger = LoggerFactory.getLogger(ShortenerController.class);

    @Autowired
    private ShortenerService shortenerService;

    @GetMapping(EndpointConstants.URL_SHORTENER)
    public String loadShortnerPage(){
        return ApplicationConstants.SHORTEN_URL_PAGE;
    }

    /**
     * Url mapping to shorten the user provided url
     *
     * @param urlDetails includes original and its shortened url
     * @param model to hold the placeholder of response
     * @return html page to display the result
     */
    @PostMapping(EndpointConstants.SHORTEN)
    public String shortenUrl(UrlDetails urlDetails, Model model){
        // service call to get the url shortened
        shortenerService.shortenUrl(urlDetails);
        model.addAttribute(ApplicationConstants.ORIGINAL_URL, urlDetails.getUrl());
        model.addAttribute(ApplicationConstants.SHORTENED_URL, urlDetails.getShortenedUrl());
        return ApplicationConstants.SHORTEN_URL_PAGE;
    }

    /**
     * Url mapping which handles the redirection of shortened url
     * to the target url
     *
     * @param urlHash value corresponding to the original url
     * @return redirect to original url
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(EndpointConstants.REDIRECT_SHORTENED_URL)
    public RedirectView loadOriginalUrl(@PathVariable String urlHash) throws ExecutionException, InterruptedException {
        // Service call to get the original url using the hash value
        UrlDetails urlDetails = shortenerService.getOriginalUrl(urlHash);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlDetails.getUrl());
        logger.info("Redirecting to {} ", urlDetails.getUrl());
        return redirectView;
    }
}
