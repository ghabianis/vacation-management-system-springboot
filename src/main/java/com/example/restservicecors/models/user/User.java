package com.example.restservicecors.models.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.restservicecors.models.Conge.Conge;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"User\"", schema = "public")
@Data
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private String id;

    @Column(name = "\"firstName\"", nullable = false, columnDefinition = "VARCHAR(50)")
    private String firstName;

    @Column(name = "\"lastName\"", nullable = false, columnDefinition = "VARCHAR(50)")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "code", columnDefinition = "VARCHAR(255)", unique = true)
    @Unique
    private String code;

    @Column(name = "roles", columnDefinition = "VARCHAR(255)[]")
    private Set<String> roles;

    @CreationTimestamp
    @Column(name = "\"createdAt\"", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "\"updatedAt\"")
    private LocalDateTime updatedAt;
    @Column(name = "\"deletedAt\"")
    private LocalDateTime deletedAt;

    @Column(name = "\"dateEmbauchement\"")
    private LocalDateTime dateEmbauchement;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conge> conges;

    public void addConge(Conge conge) {
        conges.add(conge);
        conge.setUser(this);
    }

    public void removeConge(Conge conge) {
        conges.remove(conge);
        conge.setUser(null);
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAT() {
        return deletedAt;
    }

    public LocalDateTime getDateEmbauchement() {
        return dateEmbauchement;
    }

    public void setDateEmbauchement(LocalDateTime dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}