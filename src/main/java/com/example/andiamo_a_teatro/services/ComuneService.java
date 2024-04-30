package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Comune;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    public Comune getComuneById(Long id) throws EntityNotFoundException {
        Optional<Comune> optionalComune = comuneRepository.findById(id);
        return optionalComune.orElseThrow(() -> new EntityNotFoundException(id,"Comune"));
    }

    public List<Comune> getAllComuni() {
        return comuneRepository.findAll();
    }

    public Comune createComune(Comune comune) {
        return comuneRepository.saveAndFlush(comune);
    }

    public Comune updateComune(Long id, Comune newComune) throws EntityNotFoundException {
        comuneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Comune"));
        Comune comune = Comune.builder()
                .id(id)
                .nome(newComune.getNome())
                .regione(newComune.getRegione())
                .build();
        comuneRepository.saveAndFlush(comune);
        return comune;
    }

    public void deleteComuneById(Long id) throws EntityNotFoundException {
        comuneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Comune"));
        comuneRepository.deleteById(id);
    }
}
