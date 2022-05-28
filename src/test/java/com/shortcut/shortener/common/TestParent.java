package com.shortcut.shortener.common;

import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.domains.UrlDetails;
import com.shortcut.shortener.domains.UserDetails;

/**
 *
 * This class is a parent class for test class.
 * Any common code for test class goes inside this.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
public class TestParent {

    public UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("testemail@test.com");
        userDetails.setPassword("password");
        return userDetails;
    }

    public UrlDetails getUrlDetails(){
        UrlDetails urlDetails = new UrlDetails();
        urlDetails.setUrl(ApplicationConstants.BASE_URL);
        urlDetails.setShortenedUrl(ApplicationConstants.BASE_URL+"/adb39kko");
        return urlDetails;
    }
}
