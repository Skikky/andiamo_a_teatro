package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.repositories.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpettacoloService {
    @Autowired
    private SpettacoloRepository spettacoloRepository;

    public Spettacolo getSpettacoloById(Long id) {
        Optional<Spettacolo> optionalSpettacolo = spettacoloRepository.findById(id);
        return optionalSpettacolo.orElseThrow(() -> new RuntimeException("Spettacolo non trovato con id: " + id));
    }

    public List<Spettacolo> getAllSpettacoli() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo createSpettacolo(Spettacolo spettacolo) {
        return spettacoloRepository.saveAndFlush(spettacolo);
    }

    public Spettacolo updateSpettacolo(Long id, Spettacolo newSpettacolo) {
        Spettacolo spettacolo = Spettacolo.builder()
                .id(id)
                .nome(newSpettacolo.getNome())
                .orario(newSpettacolo.getOrario())
                .durata(newSpettacolo.getDurata())
                .prezzo(newSpettacolo.getPrezzo())
                .sala(newSpettacolo.getSala())
                .genere(newSpettacolo.getGenere())
                .build();
        spettacoloRepository.saveAndFlush(spettacolo);
        return spettacolo;
    }

    public void deleteSpettacoloById(Long id) {
        spettacoloRepository.deleteById(id);
    }
}
