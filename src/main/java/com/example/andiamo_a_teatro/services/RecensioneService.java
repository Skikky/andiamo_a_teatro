package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Recensione;
import com.example.andiamo_a_teatro.repositories.RecensioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;

    public Recensione getRecensioneById(Long id) {
        Optional<Recensione> optionalRecensione = recensioneRepository.findById(id);
        return optionalRecensione.orElseThrow(() -> new RuntimeException("Recensione non trovata con id: " + id));
    }

    public List<Recensione> getAllRecensioni() {
        return recensioneRepository.findAll();
    }

    public Recensione updateRecensione(Long id, Recensione newRecensione) {
        Recensione recensione = Recensione.builder()
                .id(id)
                .testo(newRecensione.getTesto())
                .voto(newRecensione.getVoto())
                .spettacolo(newRecensione.getSpettacolo())
                .utente(newRecensione.getUtente())
                .build();
        recensioneRepository.saveAndFlush(recensione);
        return recensione;
    }

    public void deleteRecensioneById(Long id) {
        recensioneRepository.deleteById(id);
    }
}