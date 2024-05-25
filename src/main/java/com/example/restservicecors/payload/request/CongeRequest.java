package com.example.restservicecors.payload.request;

import java.time.LocalDateTime;

import com.example.restservicecors.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CongeRequest {
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateRupture;
    private User user;
    private String etat;
}
