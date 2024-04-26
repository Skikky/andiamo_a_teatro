package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.BigliettoRepository;
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
    private SpettacoloService spettacoloService;

    private Utente convertToUtente(UtenteResponse utenteResponse) {
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
                .collect(Collectors.toList());

        return utenteBuilder.bigliettiUtente(bigliettiUtente)
                .build();
    }

    private UtenteResponse convertToUtenteResponse(Utente utente) {
        List<Long> idBiglietti = Optional.ofNullable(utente.getBigliettiUtente())
                .orElse(Collections.emptyList())
                .stream()
                .map(Biglietto::getId)
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
                .build();
    }

    private BigliettoResponse convertToBigliettoResponse(Biglietto biglietto) {
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

        double prezzoBiglietto = biglietto.getSpettacolo().getPrezzo();
        if (utente.getSaldo() >= prezzoBiglietto) {
            double nuovoSaldo = utente.getSaldo() - prezzoBiglietto;
            utente.setSaldo(nuovoSaldo);
            utenteRepository.saveAndFlush(utente);

            utente.getBigliettiUtente().add(biglietto);

            biglietto.setUtente(utente);

            bigliettoRepository.saveAndFlush(biglietto);

            return BigliettoResponse.builder()
                    .id_biglietto(biglietto.getId())
                    .timestamp(biglietto.getTimestamp())
                    .id_utente(utenteId)
                    .id_spettacolo(biglietto.getSpettacolo().getId())
                    .id_posto(biglietto.getPosto().getId())
                    .build();
        } else {
            throw new IllegalArgumentException("L'utente non ha abbastanza soldi per acquistare il biglietto.");
        }
    }

    public UtenteResponse getUtenteById(Long id) {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con id: " + id));

        return convertToUtenteResponse(utente);
    }

    public List<UtenteResponse> getAllUtenti() {
        return utenteRepository.findAll().stream().map(this::convertToUtenteResponse).toList();
    }

    public UtenteResponse createUtente(Utente utente) {
        utenteRepository.saveAndFlush(utente);
        return convertToUtenteResponse(utente);
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
        return convertToUtenteResponse(utente);
    }

    public void deleteUtenteById(Long id) {
        utenteRepository.deleteById(id);
    }
}
