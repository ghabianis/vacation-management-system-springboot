package com.example.restservicecors.service.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restservicecors.models.Conge.Conge;

@Repository
public interface CongeRepository extends JpaRepository<Conge, String> {}