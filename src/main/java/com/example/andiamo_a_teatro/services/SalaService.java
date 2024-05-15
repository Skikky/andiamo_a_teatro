package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Sala;
import com.example.andiamo_a_teatro.entities.Sede;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.SalaRepository;
import com.example.andiamo_a_teatro.request.SalaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private SedeService sedeService;

    public Sala getSalaById(Long id) throws EntityNotFoundException {
        Optional<Sala> optionalSala = salaRepository.findById(id);
        return optionalSala.orElseThrow(() -> new EntityNotFoundException(id,"Sede"));
    }

    public List<Sala> getAllSale() {
        return salaRepository.findAll();
    }

    public Sala createSala(SalaRequest salaRequest) throws EntityNotFoundException {
        Sede sede = sedeService.getSedeById(salaRequest.getId_sede());
        Sala sala = Sala.builder()
                .nome(salaRequest.getNome())
                .posti(salaRequest.getPosti())
                .sede(sede)
                .build();
        return salaRepository.saveAndFlush(sala);
    }

    public Sala updateSala(Long id, Sala newSala) throws EntityNotFoundException {
        getSalaById(id);
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
        getSalaById(id);
        salaRepository.deleteById(id);
    }
}
