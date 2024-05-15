package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Comune;
import com.example.andiamo_a_teatro.entities.Sede;
import com.example.andiamo_a_teatro.repositories.SedeRepository;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.request.SedeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private ComuneService comuneService;

    public Sede getSedeById(Long id) throws EntityNotFoundException {
        Optional<Sede> optionalSede = sedeRepository.findById(id);
        return optionalSede.orElseThrow(() -> new EntityNotFoundException(id,"Sede"));
    }

    public List<Sede> getAllSedi() {
        return sedeRepository.findAll();
    }

    public Sede createSede(SedeRequest sedeRequest) throws EntityNotFoundException {
        Comune comune = comuneService.getComuneById(sedeRequest.getId_comune());
        Sede sede = Sede.builder()
                .indirizzo(sedeRequest.getIndirizzo())
                .nome(sedeRequest.getNome())
                .isOpen(sedeRequest.getIsOpen())
                .comune(comune)
                .build();
        return sedeRepository.saveAndFlush(sede);
    }

    public Sede updateSede(Long id, Sede newSede) throws EntityNotFoundException {
        getSedeById(id);
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

    public void deleteSedeById(Long id) throws EntityNotFoundException {
        getSedeById(id);
        sedeRepository.deleteById(id);
    }
}
