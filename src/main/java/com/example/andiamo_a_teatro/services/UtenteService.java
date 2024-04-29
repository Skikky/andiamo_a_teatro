package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Recensione;
import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.BigliettoRepository;
import com.example.andiamo_a_teatro.repositories.RecensioneRepository;
import com.example.andiamo_a_teatro.repositories.SpettacoloRepository;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.response.UtenteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private BigliettoRepository bigliettoRepository;
    @Autowired
    private SpettacoloRepository spettacoloRepository;
    @Autowired
    private RecensioneRepository recensioneRepository;

    private Utente mapToUtente(UtenteResponse utenteResponse) {
        Utente.UtenteBuilder utenteBuilder = Utente.builder()
                .id(utenteResponse.getId_utente())
                .nome(utenteResponse.getNome())
                .cognome(utenteResponse.getCognome())
                .email(utenteResponse.getEmail())
                .indirizzo(utenteResponse.getIndirizzo())
                .telefono(utenteResponse.getTelefono())
                .nascita(utenteResponse.getNascita())
                .password(utenteResponse.getPassword())
                .saldo(utenteResponse.getSaldo());

        List<Biglietto> bigliettiUtente = utenteResponse.getId_biglietto().stream()
                .map(bigliettoRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        List<Recensione> recensioniUtente = utenteResponse.getId_recensione().stream()
                .map(recensioneRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return utenteBuilder
                .bigliettiUtente(bigliettiUtente)
                .recensioniUtente(recensioniUtente)
                .build();
    }

    private UtenteResponse mapToUtenteResponse(Utente utente) {
        List<Long> idBiglietti = Optional.ofNullable(utente.getBigliettiUtente())
                .orElse(Collections.emptyList())
                .stream()
                .map(Biglietto::getId)
                .collect(Collectors.toList());
        List<Long> idRecensioni = Optional.ofNullable(utente.getRecensioniUtente())
                .orElse(Collections.emptyList())
                .stream()
                .map(Recensione::getId)
                .collect(Collectors.toList());

        return UtenteResponse.builder()
                .id_utente(utente.getId())
                .nome(utente.getNome())
                .cognome(utente.getCognome())
                .email(utente.getEmail())
                .indirizzo(utente.getIndirizzo())
                .telefono(utente.getTelefono())
                .nascita(utente.getNascita())
                .password(utente.getPassword())
                .saldo(utente.getSaldo())
                .id_biglietto(idBiglietti)
                .id_recensione(idRecensioni)
                .build();
    }

    private BigliettoResponse mapToBigliettoResponse(Biglietto biglietto) {
        return BigliettoResponse.builder()
                .id_biglietto(biglietto.getId())
                .timestamp(biglietto.getTimestamp())
                .id_utente(biglietto.getUtente().getId())
                .id_spettacolo(biglietto.getSpettacolo().getId())
                .id_posto(biglietto.getPosto().getId())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BigliettoResponse acquistaBiglietto(Long utenteId, Long bigliettoId) throws IllegalArgumentException {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + utenteId));

        Biglietto biglietto = bigliettoRepository.findById(bigliettoId)
                .orElseThrow(() -> new IllegalArgumentException("Biglietto non trovato con ID: " + bigliettoId));

        if (biglietto.getUtente() != null) {
            throw new IllegalArgumentException("Il biglietto è già stato acquistato.");
        }

        double prezzoBiglietto = biglietto.getSpettacolo().getPrezzo();
        if (utente.getSaldo() >= prezzoBiglietto) {
            double nuovoSaldo = utente.getSaldo() - prezzoBiglietto;
            utente.setSaldo(nuovoSaldo);
            utenteRepository.saveAndFlush(utente);

            utente.getBigliettiUtente().add(biglietto);
            biglietto.setUtente(utente);

            bigliettoRepository.saveAndFlush(biglietto);

            return mapToBigliettoResponse(biglietto);
        } else {
            throw new IllegalArgumentException("L'utente non ha abbastanza soldi per acquistare il biglietto.");
        }
    }

    public UtenteResponse getUtenteById(Long id) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con id: " + id));

        return mapToUtenteResponse(utente);
    }

    public List<UtenteResponse> getAllUtenti() {
        return utenteRepository.findAll().stream().map(this::mapToUtenteResponse).toList();
    }

    public UtenteResponse createUtente(Utente utente) {
        utenteRepository.saveAndFlush(utente);
        return mapToUtenteResponse(utente);
    }

    public UtenteResponse updateUtente(Long id, Utente newUtente) {
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
        return mapToUtenteResponse(utente);
    }

    public void deleteUtenteById(Long id) {
        utenteRepository.deleteById(id);
    }

    public Recensione scriviRecensione(Long utenteId, Long spettacoloId, String testo, int voto) {
        Optional<Utente> utente = utenteRepository.findById(utenteId);
        if (utente.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato con id: " + utenteId);
        }

        Optional<Spettacolo> spettacolo = spettacoloRepository.findById(spettacoloId);
        if (spettacolo.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato con id: " + spettacoloId);
        }

        Recensione recensione = Recensione.builder()
                .testo(testo)
                .voto(voto)
                .spettacolo(spettacolo.get())
                .utente(utente.get())
                .build();

        return recensioneRepository.saveAndFlush(recensione);
    }

}
