package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Recensione;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.RecensioneRepository;
import com.example.andiamo_a_teatro.response.RecensioneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecensioneService {

    @Autowired
    private RecensioneRepository recensioneRepository;

    private RecensioneResponse mapToRecensioneResponse(Recensione recensione) {
        if (recensione == null) {
            return null;
        }

        return RecensioneResponse.builder()
                .testo(recensione.getTesto())
                .voto(recensione.getVoto())
                .timestamp(recensione.getTimestamp())
                .id_spettacolo(recensione.getSpettacolo().getId())
                .id_utente(recensione.getUtente().getId())
                .build();
    }
/*
    public Recensione mapToRecensione(RecensioneResponse recensioneResponse) throws EntityNotFoundException {
        if (recensioneResponse == null) {
            return null;
        }

        Spettacolo spettacolo = spettacoloRepository.findById(recensioneResponse.getId_spettacolo())
                .orElseThrow(() -> new EntityNotFoundException("Spettacolo non trovato con ID: " + recensioneResponse.getId_spettacolo()));

        Utente utente = utenteRepository.findById(recensioneResponse.getId_utente())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con ID: " + recensioneResponse.getId_utente()));

        return Recensione.builder()
                .testo(recensioneResponse.getTesto())
                .voto(recensioneResponse.getVoto())
                .timestamp(recensioneResponse.getTimestamp())
                .spettacolo(spettacolo)
                .utente(utente)
                .build();
    }
*/
public RecensioneResponse getRecensioneById(Long id) throws EntityNotFoundException {
    Recensione recensione = recensioneRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, "Recensione"));
    return mapToRecensioneResponse(recensione);
}

    public List<RecensioneResponse> getAllRecensioni() {
        return recensioneRepository.findAll().stream().map(this::mapToRecensioneResponse).toList();
    }

    public RecensioneResponse updateRecensione(Long id, String testo, Integer voto) throws EntityNotFoundException {
        getRecensioneById(id);
        Recensione recensione = Recensione.builder()
                .id(id)
                .testo(testo)
                .voto(voto)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        recensioneRepository.saveAndFlush(recensione);
        return mapToRecensioneResponse(recensione);
    }

    public void deleteRecensioneById(Long id) throws EntityNotFoundException {
        getRecensioneById(id);
        recensioneRepository.deleteById(id);
    }
}