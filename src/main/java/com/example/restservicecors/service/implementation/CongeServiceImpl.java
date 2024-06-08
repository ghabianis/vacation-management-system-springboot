package com.example.restservicecors.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservicecors.models.Conge.Conge;
import com.example.restservicecors.service.CongeService;
import com.example.restservicecors.service.respositories.CongeRepository;

@Service
public class CongeServiceImpl implements CongeService {

    private final CongeRepository congeRepository;

    @Autowired
    public CongeServiceImpl(CongeRepository congeRepository) {
        this.congeRepository = congeRepository;
    }

    @Override
    public Conge getById(String id) {
        Optional<Conge> conge = congeRepository.findById(id);
        return conge.orElseThrow(() -> new IllegalArgumentException("Invalid conge Id: " + id));
    }

    @Override
    public Conge getByName(String name) {
        // Implement the logic to find Conge by name if you have such a method in your
        // repository
        // Example: return congeRepository.findByName(name);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Conge> findAll() {
        return congeRepository.findAll();
    }

    @Override
    public Conge save(Conge conge) {
        if (conge.getEtat() == null) {
            conge.setEtat("SOLLICITE");
        }
        return congeRepository.save(conge);
    }

    @Override
    public Conge update(Conge conge, String id) {
        System.out.println(conge);
        Optional<Conge> existingConge = congeRepository.findById(id);
        if (existingConge.isPresent()) {
            Conge updatedConge = existingConge.get();
            updatedConge.setDescription(conge.getDescription());
            updatedConge.setEtat(conge.getEtat());
            // Do not update dateDebut and dateFin
            return congeRepository.save(updatedConge);
        } else {
            throw new IllegalArgumentException("Invalid conge Id: " + id);
        }
    }

    @Override
    public void delete(String id) {
        Conge conge = getById(id);
        congeRepository.delete(conge);
    }
}