package com.example.restservicecors.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restservicecors.dto.user.UserDto;
import com.example.restservicecors.models.Conge.Conge;
import com.example.restservicecors.service.CongeService;

import io.minio.messages.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Controller
@RequestMapping(value = "/conge")
public class CongeController {
    private final CongeService congeService;

    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    @GetMapping(value = "/all/conges", produces = MediaType.APPLICATION_JSON_VALUE)
    @QueryMapping(name = "all conges")
    @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "find all conges")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "conges fteched successfully", content = {
                    @Content(schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "conges not found", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
    @PreAuthorize("hasRole('employee')")
    public List<Conge> findAll() {
        return congeService.findAll();
    }
    /*
     * @RequestMapping(value = "/all", method = RequestMethod.GET)
     * public String findAll(Model model) {
     * List<Conge> conges = congeService.findAll();
     * model.addAttribute("conges", conges);
     * return "conge-list";
     * }
     */

    // @GetMapping("/add")
    // public String showAddForm(Model model) {
    // model.addAttribute("conge", new Conge());
    // return "conge-add";
    // }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Conge conge = congeService.getById(id);
        model.addAttribute("conge", conge);
        return "conge-edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteConge(@PathVariable("id") String id) {
        congeService.delete(id);
        return "redirect:/conge/all";
    }

    @GetMapping("/add/form")
    public String showAddForm(Model model) {
        model.addAttribute("conge", new Conge());
        return "conge-add";
    }

    @PostMapping("/add")
    public String saveConge(@ModelAttribute Conge conge) {
        conge.setEtat(Conge.Etat.SOLLICITE);
        congeService.save(conge);
        return "redirect:/home"; // Redirect to a page showing all conges or a success page
    }

    @PostMapping("/update/{id}")
    public String updateConge(@PathVariable("id") String id, Conge conge) {
        congeService.update(conge, id);
        return "redirect:/home";
    }
}
