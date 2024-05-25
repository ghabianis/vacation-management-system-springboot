package com.example.restservicecors.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restservicecors.models.Conge.Conge;
import com.example.restservicecors.service.CongeService;
import com.example.restservicecors.service.respositories.CongeRepository;

@Service
@Transactional
public class CongeServiceImpl implements CongeService {

    private final CongeRepository congeRepository;

    @Autowired
    public CongeServiceImpl(CongeRepository congeRepository) throws Exception {
        this.congeRepository = congeRepository;
    }

    @Override
    public Conge getById(String id) {
        Conge result = congeRepository.getById(id);
        return result;
    }

    @Override
    public Conge getByNamConge(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNamConge'");
    }

    @Override
    public List<Conge> findAll() {
        List<Conge> listConge = congeRepository.findAll();
        return listConge;
    }

    @Override
    public Conge save(Conge conge) {
        return congeRepository.save(conge);
    }

    @Override
    public Conge update(Conge conge, String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
