package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.*;
import com.example.andiamo_a_teatro.enums.Role;
import com.example.andiamo_a_teatro.exception.BigliettoNonDisponibileException;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.exception.PoveroException;
import com.example.andiamo_a_teatro.exception.SpettacoloNonVistoException;
import com.example.andiamo_a_teatro.repositories.*;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.response.UtenteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private RecensioneRepository recensioneRepository;
    @Autowired
    private ComuneRepository comuneRepository;

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

        Comune comune = null;
        if (utenteResponse.getId_comune() != null) {
            Optional<Comune> comuneOptional = comuneRepository.findById(utenteResponse.getId_comune());
            comune = comuneOptional.orElse(null);
        }

        return utenteBuilder
                .bigliettiUtente(bigliettiUtente)
                .recensioniUtente(recensioniUtente)
                .comune(comune)
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

        Long idComune = Optional.ofNullable(utente.getComune())
                .map(Comune::getId)
                .orElse(null);

        return UtenteResponse.builder()
                .id_utente(utente.getId())
                .id_comune(idComune)
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
    public BigliettoResponse acquistaBiglietto(Long utenteId, Long bigliettoId) throws PoveroException, BigliettoNonDisponibileException, EntityNotFoundException {
        UtenteResponse utenteResponse = getUtenteById(utenteId);
        Utente utente = mapToUtente(utenteResponse);

        Biglietto biglietto = bigliettoRepository.findById(bigliettoId)
                .orElseThrow(() -> new EntityNotFoundException(bigliettoId,"Biglietto"));

        if (biglietto.getUtente() != null) {
            throw new BigliettoNonDisponibileException();
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
            throw new PoveroException();
        }
    }

    public UtenteResponse getUtenteById(Long id) throws EntityNotFoundException {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id,"Utente"));

        return mapToUtenteResponse(utente);
    }

    public List<UtenteResponse> getAllUtenti() {
        return utenteRepository.findAll().stream().map(this::mapToUtenteResponse).toList();
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
                .build();
        utenteRepository.saveAndFlush(utente);
        return mapToUtenteResponse(utente);
    }

    public void deleteUtenteById(Long id) throws EntityNotFoundException {
        getUtenteById(id);
        utenteRepository.deleteById(id);
    }

    public Recensione scriviRecensione(Long utenteId, Long spettacoloId, String testo, int voto) throws SpettacoloNonVistoException, EntityNotFoundException {
        UtenteResponse utenteResponse = getUtenteById(utenteId);
        Utente utente = mapToUtente(utenteResponse);

        Spettacolo spettacolo = spettacoloService.getSpettacoloById(spettacoloId);

        boolean visto = utente.getBigliettiUtente().stream()
                .anyMatch(biglietto -> biglietto.getSpettacolo().getId().equals(spettacoloId)
                        && biglietto.getSpettacolo().getOrario().isBefore(LocalDateTime.now()));

        if (!visto) {
            throw new SpettacoloNonVistoException();
        }

        Recensione recensione = Recensione.builder()
                .testo(testo)
                .voto(voto)
                .spettacolo(spettacolo)
                .utente(utente)
                .build();

        return recensioneRepository.saveAndFlush(recensione);
    }

    public void updateRole(Long id, String new_role) throws EntityNotFoundException {
        UtenteResponse utenteResponse = getUtenteById(id);
        Utente utente = mapToUtente(utenteResponse);
        utente.setRole(Role.valueOf(new_role));
        utenteRepository.saveAndFlush(utente);
    }
}
