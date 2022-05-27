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

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(EndpointConstants.HOME)
    public ModelAndView registerUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ApplicationConstants.VIEW_REGISTRATION);
        return modelAndView;
    }

    @PostMapping(EndpointConstants.REGISTER)
    public String register(UserDetails userDetails){
        return userService.registerUser(userDetails) ? "redirect:/login" : "/registrationerror";
    }

}
