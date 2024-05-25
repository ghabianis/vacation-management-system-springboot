package com.example.restservicecors.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restservicecors.models.Conge.Conge;
import com.example.restservicecors.service.CongeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Controller
@RequestMapping("/conge")
public class CongeController {
    private final CongeService congeService;

    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    @GetMapping(value = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    @QueryMapping(name = "all conges")
    @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "find all conges")
    @PreAuthorize("hasRole('admin')")
    public List<Conge> findAll() {
        return congeService.findAll();
    }

    @PostMapping(value = "/add")
    public Conge save(Conge conge) {
        System.out.println("hello create " + conge);
        return congeService.save(conge);
    }
}