package com.shortcut.shortener.controllers;

import com.shortcut.shortener.constants.ApplicationConstants;
import com.shortcut.shortener.constants.EndpointConstants;
import com.shortcut.shortener.domains.UserDetails;
import com.shortcut.shortener.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User service to load registration page and register the user.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Url mapping method which loads the home / user registration page
     *
     * @return ModelAndView object which loads registration page view
     */
    @GetMapping(EndpointConstants.HOME)
    public ModelAndView registerUserPageLoad() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ApplicationConstants.REGISTRATION_PAGE);
        return modelAndView;
    }

    /**
     * Url mapping to register the user to the appilcation
     *
     * @param userDetails including email and password
     * @return redirect to login page or error page depending upon registration success.
     */
    @PostMapping(EndpointConstants.REGISTER)
    public String registerUser(UserDetails userDetails){
        // Service method to get the user registred to datastore
        return userService.registerUser(userDetails) ?
                ApplicationConstants.REDIRECT_LOGIN : ApplicationConstants.REGISTRATION_ERROR_PAGE;
    }

}
