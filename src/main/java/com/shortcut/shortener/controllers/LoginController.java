package com.shortcut.shortener.controllers;

import com.shortcut.shortener.constants.EndpointConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(EndpointConstants.LOGIN)
    public String loadLoginPage(){
        return "redirect:/urlshortener";
    }
}
