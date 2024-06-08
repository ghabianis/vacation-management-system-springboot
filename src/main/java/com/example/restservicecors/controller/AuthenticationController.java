package com.example.restservicecors.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservicecors.models.Conge.Conge;
import com.example.restservicecors.payload.request.CongeRequest;
import com.example.restservicecors.payload.request.SignUpRequest;
import com.example.restservicecors.payload.request.SigninRequest;
import com.example.restservicecors.payload.response.JwtAuthenticationResponse;
import com.example.restservicecors.service.AuthenticationService;
import com.example.restservicecors.service.CongeService;

import lombok.RequiredArgsConstructor;

@RestController("Authentication")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final CongeService congeService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PutMapping("/edit/{id}")
    public Conge editConge(@PathVariable("id") String id, @RequestBody CongeRequest conge) {
        System.out.println("******************************");
        System.out.println(conge);
        Conge congeData = new Conge();
        congeData.setEtat(conge.getEtat());
        congeData.setDateDebut(conge.getDateDebut());
        congeData.setDateFin(conge.getDateFin());
        congeData.setDescription(conge.getDescription());
        congeData.setUser(conge.getUser());
        System.out.println("******************************");
        return congeService.update(congeData, id);
    }

    @DeleteMapping("/delete/{id}")
    public Conge deleteConge(@PathVariable("id") String id) {
        congeService.delete(id);
        return congeService.getById(id);
    }

}