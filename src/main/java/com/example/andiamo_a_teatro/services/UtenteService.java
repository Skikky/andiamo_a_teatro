package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.BigliettoRepository;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private BigliettoRepository bigliettoRepository;
    @Autowired
    private SpettacoloService spettacoloService;

    public Biglietto acquistaBiglietto(Long utenteId, Long bigliettoId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + utenteId));

        Biglietto biglietto = bigliettoRepository.findById(bigliettoId).orElseThrow(() -> new RuntimeException("Biglietto non trovato con ID: " + bigliettoId));
        Spettacolo spettacolo = biglietto.getSpettacolo();

        if (utente.getBigliettiUtente().contains(biglietto)) {
            throw new RuntimeException("Il biglietto con ID " + bigliettoId + " è già associato all'utente con ID " + utenteId);
        }

        Double prezzoSpettacolo = spettacolo.getPrezzo();
        if (utente.getSaldo() < prezzoSpettacolo) {
            throw new RuntimeException("Saldo insufficiente per acquistare il biglietto");
        }

        Double nuovoSaldo = utente.getSaldo() - prezzoSpettacolo;
        utente.setSaldo(nuovoSaldo);

        biglietto.setUtente(utente);
        utente.getBigliettiUtente().add(biglietto);
        utenteRepository.saveAndFlush(utente);
        return biglietto;
    }

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
