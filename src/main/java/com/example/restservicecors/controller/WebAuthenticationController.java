package com.example.restservicecors.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.example.restservicecors.models.Greeting;
import com.example.restservicecors.payload.request.SigninRequest;
import com.example.restservicecors.service.AuthenticationService;

@Controller
@RequestMapping("/web/auth")
public class WebAuthenticationController {
    
    private final AuthenticationService authenticationService;

    @Autowired
    public WebAuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // @GetMapping("/login")
    // public String showLoginForm(Model model) {
    //     if (!model.containsAttribute("signinRequest")) {
    //         model.addAttribute("signinRequest", new SigninRequest());
    //     }
    //     return "login";
    // }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute SigninRequest signinRequest, Model model) {
        try {
            model.addAttribute("signinRequest", signinRequest);
            // Call the authentication service to authenticate the user
            authenticationService.signin(signinRequest);
            // Redirect to the appropriate page after successful login
            return "home";
        } catch (Exception e) {
            // Add an error message to the model if authentication fails
            model.addAttribute("error", e.getMessage());
            // Add the signinRequest back to the model to populate the login form
            model.addAttribute("signinRequest", signinRequest);
            return "login";
        }
    }



}