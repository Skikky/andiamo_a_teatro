package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Sede;
import com.example.andiamo_a_teatro.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {
    @Autowired
    private SedeRepository sedeRepository;

    public Sede getSedeById(Long id) {
        Optional<Sede> optionalSede = sedeRepository.findById(id);
        return optionalSede.orElseThrow(() -> new RuntimeException("Sede non trovato con id: " + id));
    }

    public List<Sede> getAllSedi() {
        return sedeRepository.findAll();
    }

    public Sede createSede(Sede utente) {
        return sedeRepository.saveAndFlush(utente);
    }

    public Sede updateSede(Long id, Sede newSede) {
        Sede sede = Sede.builder()
                .id(id)
                .nome(newSede.getNome())
                .isOpen(newSede.getIsOpen())
                .indirizzo(newSede.getIndirizzo())
                .comune(newSede.getComune())
                .build();
        sedeRepository.saveAndFlush(sede);
        return sede;
    }

    public void deleteSedeById(Long id) {
        sedeRepository.deleteById(id);
    }
}
