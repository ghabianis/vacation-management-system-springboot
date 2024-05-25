package com.example.restservicecors.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservicecors.dto.user.UserDto;
import com.example.restservicecors.models.user.User;
import com.example.restservicecors.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController("User")

@RequestMapping("/api/v1")
public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
                this.userService = userService;
        }

        @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
        @QueryMapping(name = "create user")
        @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "Create a new user")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                                        @Content(schema = @Schema(implementation = UserDto.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
        @PreAuthorize("hasRole('user')")
        public User save(@RequestBody User user) {
                System.out.println("hello create " + user);
                return userService.save(user);
        }

        @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
        @QueryMapping(name = "all user")
        @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "find all users")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                                        @Content(schema = @Schema(implementation = UserDto.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
        @PreAuthorize("hasRole('user')")
        public List<User> findAll() {
                return userService.findAll();
        }

        @GetMapping(value = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "Get one User")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                                        @Content(schema = @Schema(implementation = UserDto.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
        @PreAuthorize("hasRole('user')")
        @QueryMapping(name = "get User By Id")
        public User getById(@PathVariable @Argument final String id) {
                final User user = userService.getById(id);
                return user;
        }

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @QueryMapping(name = "delete user")
        @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "delete One user")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                                        @Content(schema = @Schema(implementation = UserDto.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
        @PreAuthorize("hasRole('user')")
        public void delete(@PathVariable @Argument final String id) {
                userService.delete(id);
        }

        @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @QueryMapping(name = "update user")
        @Operation(security = @SecurityRequirement(name = "basicAuth"), summary = "update One user")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "User created successfully", content = {
                                        @Content(schema = @Schema(implementation = UserDto.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access forbidden", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "User not found", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                                        @Content(schema = @Schema(implementation = ErrorResponse.class)) }) })
        @PreAuthorize("hasRole('user')")
        public void update(@PathVariable @Argument final String id, @RequestBody User user) {
                userService.update(user, id);
        }
}
