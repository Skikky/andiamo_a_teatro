package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Sala;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;

    public Sala getSalaById(Long id) throws EntityNotFoundException {
        Optional<Sala> optionalSala = salaRepository.findById(id);
        return optionalSala.orElseThrow(() -> new EntityNotFoundException(id,"Sede"));
    }

    public List<Sala> getAllSale() {
        return salaRepository.findAll();
    }

    public Sala createSala(Sala sala) {
        return salaRepository.saveAndFlush(sala);
    }

    public Sala updateSala(Long id, Sala newSala) throws EntityNotFoundException {
        salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Sala"));
        Sala sala = Sala.builder()
                .id(id)
                .nome(newSala.getNome())
                .posti(newSala.getPosti())
                .sede(newSala.getSede())
                .build();
        salaRepository.saveAndFlush(sala);
        return sala;
    }

    public void deleteSalaById(Long id) throws EntityNotFoundException {
        salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Sala"));
        salaRepository.deleteById(id);
    }
}
