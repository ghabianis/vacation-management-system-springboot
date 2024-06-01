package com.example.restservicecors.models.Conge;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.example.restservicecors.models.user.User;
import com.example.restservicecors.serializers.CongeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

/*
 *  • Un « id » : identificateur unique (généré automatiquement) 
    • Une « description » : chaine de caractères 
    • Une date de début 
    • Une date de fin
 */
@Entity
@Builder

@Table(name = "\"Conge\"", schema = "public")
@Data
@JsonSerialize(using = CongeSerializer.class)
public class Conge {
    public enum Etat {
        SOLLICITE, VALIDE, REFUSE, ANNULE, EN_COURS, ARRETE, FINI
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;

    @Column(name = "description", nullable = true, unique = false)
    private String description;

    @CreationTimestamp
    @Column(name = "\"dateDebut\"", nullable = false, updatable = false)
    private LocalDateTime dateDebut;

    @CreationTimestamp
    @Column(name = "\"dateFin\"", nullable = false, updatable = false)
    private LocalDateTime dateFin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "\"dateRupture\"", nullable = true)
    private LocalDateTime dateRupture;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private Etat etat;

    // Default constructor
    public Conge() {
    }

    // Parameterized constructor
    public Conge(String id, String description, LocalDateTime dateDebut, LocalDateTime dateFin, User user,
            LocalDateTime dateRupture, Etat etat) {
        this.id = id;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.user = user;
        this.dateRupture = dateRupture;
        this.etat = (etat != null) ? etat : Etat.SOLLICITE;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDatDebut() {
        return dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDateRupture() {
        return dateRupture;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDateRupture(LocalDateTime dateRupture) {
        this.dateRupture = dateRupture;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

}
