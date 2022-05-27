package com.shortcut.shortener.controllers;

import com.shortcut.shortener.constants.EndpointConstants;
import com.shortcut.shortener.domains.UrlDetails;
import com.shortcut.shortener.services.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;

@Controller
public class ShortenerController {

    @Autowired
    private ShortenerService shortenerService;

    @GetMapping(EndpointConstants.URL_SHORTENER)
    public String loadShortnerForm(){
        return "shorturl";
    }

    @PostMapping(EndpointConstants.SHORTEN)
    public String shortenUrl(UrlDetails urlDetails, Model model){
        shortenerService.shortenUrl(urlDetails);
        model.addAttribute("originalurl", urlDetails.getUrl());
        model.addAttribute("shortenedurl", urlDetails.getShortenedUrl());
        return "shorturl";
    }

    @GetMapping(EndpointConstants.SHORTENED_URL)
    public RedirectView getFooById(@PathVariable String urlHash,
                                   HttpServletResponse httpServletResponse) throws ExecutionException, InterruptedException {
        UrlDetails urlDetails = shortenerService.getOriginalUrl(urlHash);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlDetails.getUrl());
        return redirectView;
    }
}
