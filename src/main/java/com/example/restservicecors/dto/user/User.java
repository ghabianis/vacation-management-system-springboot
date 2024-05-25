package com.example.restservicecors.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
@Schema(description = "User DTO")
public class User {
    @NotNull
    @Schema(description = "User id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private String id;

    @NotNull
    @Schema(description = "User first name", example = "ghabi")
    private String firstName;

    @NotNull
    @Schema(description = "User last name", example = "anis")
    private String lastName;

    @NotNull
    @Schema(description = "User username", example = "anis.ghabi@ipsas.tn")
    private String username;

    @Schema(description = "User crypted password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Schema(description = "User roles")
    private Set<String> roles;

}