package com.example.restservicecors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restservicecors.payload.request.SigninRequest;
import com.example.restservicecors.payload.response.JwtAuthenticationResponse;
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
    // if (!model.containsAttribute("signinRequest")) {
    // model.addAttribute("signinRequest", new SigninRequest());
    // }
    // return "login";
    // }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute SigninRequest signinRequest, Model model) {
        try {
            JwtAuthenticationResponse response = authenticationService.signin(signinRequest);
            model.addAttribute("signinRequest", response);
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