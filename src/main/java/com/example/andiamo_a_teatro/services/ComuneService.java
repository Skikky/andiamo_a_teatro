package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Comune;
import com.example.andiamo_a_teatro.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    public Comune getComuneById(Long id) {
        Optional<Comune> optionalComune = comuneRepository.findById(id);
        return optionalComune.orElseThrow(() -> new RuntimeException("Comune non trovato con id: " + id));
    }

    public List<Comune> getAllComuni() {
        return comuneRepository.findAll();
    }

    public Comune createComune(Comune comune) {
        return comuneRepository.saveAndFlush(comune);
    }

    public Comune updateComune(Long id, Comune newComune) {
        Comune comune = Comune.builder()
                .id(id)
                .nome(newComune.getNome())
                .regione(newComune.getRegione())
                .build();
        comuneRepository.saveAndFlush(comune);
        return comune;
    }

    public void deleteComuneById(Long id) {
        comuneRepository.deleteById(id);
    }
}
