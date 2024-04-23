package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    public Utente getUtenteById(Long id) {
        Optional<Utente> optionalUtente = utenteRepository.findById(id);
        return optionalUtente.orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }

    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente createUtente(Utente utente) {
        return utenteRepository.saveAndFlush(utente);
    }

    public Utente updateUtente(Long id, Utente newUtente) {
        Utente utente = Utente.builder()
                .id(id)
                .nome(newUtente.getNome())
                .cognome(newUtente.getCognome())
                .email(newUtente.getEmail())
                .indirizzo(newUtente.getIndirizzo())
                .telefono(newUtente.getTelefono())
                .nascita(newUtente.getNascita())
                .password(newUtente.getPassword())
                .isLoggato(newUtente.getIsLoggato())
                .build();
        utenteRepository.saveAndFlush(utente);
        return utente;
    }

    public void deleteUtenteById(Long id) {
        utenteRepository.deleteById(id);
    }
}
