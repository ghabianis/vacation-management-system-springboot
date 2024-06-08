package com.example.restservicecors.service;

import java.util.List;

import com.example.restservicecors.models.Conge.Conge;

public interface CongeService {

    Conge getById(final String id);

    Conge getByName(final String name);

    List<Conge> findAll();

    Conge save(final Conge conge);

    Conge update(final Conge conge, final String id);

    void delete(final String id);

}
