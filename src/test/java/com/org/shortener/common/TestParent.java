package com.org.shortener.common;

import com.org.shortener.constants.ApplicationConstants;
import com.org.shortener.domains.UrlDetails;
import com.org.shortener.domains.UserDetails;

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

    /**
     * Get dummy user details test data
     *
     * @return
     */
    public UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("testemail@test.com");
        userDetails.setPassword("password");
        return userDetails;
    }

    /**
     * Get dummy url details test data
     *
     * @return
     */
    public UrlDetails getUrlDetails(){
        UrlDetails urlDetails = new UrlDetails();
        urlDetails.setUrl(ApplicationConstants.BASE_URL);
        urlDetails.setShortenedUrl(ApplicationConstants.BASE_URL+"/adb39kko");
        return urlDetails;
    }
}
