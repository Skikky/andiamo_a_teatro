package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.repositories.BigliettoRepository;
import com.example.andiamo_a_teatro.repositories.PostoRepository;
import com.example.andiamo_a_teatro.repositories.SpettacoloRepository;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import com.example.andiamo_a_teatro.request.BigliettoRequest;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BigliettoService {
    @Autowired
    private BigliettoRepository bigliettoRepository;
    @Autowired
    private SpettacoloRepository spettacoloRepository;
    @Autowired
    private PostoRepository postoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    private Utente mapToUtente(Long utenteId) {
        return utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + utenteId));
    }

    private Spettacolo mapToSpettacolo(Long spettacoloId) {
        return spettacoloRepository.findById(spettacoloId)
                .orElseThrow(() -> new RuntimeException("Spettacolo non trovato con id: " + spettacoloId));
    }

    private Posto mapToPosto(Long postoId) {
        return postoRepository.findById(postoId)
                .orElseThrow(() -> new RuntimeException("Posto non trovato con id: " + postoId));
    }

    private BigliettoResponse mapBigliettoToResponse(Biglietto biglietto) {
        Long idUtente = (biglietto.getUtente() != null) ? biglietto.getUtente().getId() : null;
        return BigliettoResponse.builder()
                .id_biglietto(biglietto.getId())
                .timestamp(biglietto.getTimestamp())
                .id_utente(idUtente)
                .id_spettacolo(biglietto.getSpettacolo().getId())
                .id_posto(biglietto.getPosto().getId())
                .build();
    }

    public BigliettoResponse getBigliettoById(Long id) {
        Biglietto biglietto = bigliettoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Biglietto non trovato con id: " + id));
        return mapBigliettoToResponse(biglietto);
    }

    public List<BigliettoResponse> getAllBiglietti() {
        List<Biglietto> biglietti = bigliettoRepository.findAll();
        List<BigliettoResponse> bigliettiDTO = new ArrayList<>();

        for (Biglietto biglietto : biglietti) {
            BigliettoResponse bigliettoResponse = mapBigliettoToResponse(biglietto);
            bigliettiDTO.add(bigliettoResponse);
        }

        return bigliettiDTO;
    }

    public BigliettoResponse createBiglietto(BigliettoRequest bigliettoRequest) {
        Spettacolo spettacolo = mapToSpettacolo(bigliettoRequest.getId_spettacolo());
        Posto posto = mapToPosto(bigliettoRequest.getId_posto());
        Biglietto biglietto = Biglietto.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .spettacolo(spettacolo)
                .posto(posto)
                .build();
        bigliettoRepository.saveAndFlush(biglietto);
        return mapBigliettoToResponse(biglietto);
    }

    public BigliettoResponse updateBiglietto(Long id, BigliettoResponse bigliettoResponse) {
        Utente utente = mapToUtente(bigliettoResponse.getId_utente());
        Spettacolo spettacolo = mapToSpettacolo(bigliettoResponse.getId_spettacolo());
        Posto posto = mapToPosto(bigliettoResponse.getId_posto());
        Biglietto biglietto = Biglietto.builder()
                .id(id)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .utente(utente)
                .spettacolo(spettacolo)
                .posto(posto)
                .build();
        bigliettoRepository.saveAndFlush(biglietto);
        return mapBigliettoToResponse(biglietto);
    }

    public void deleteBigliettoById(Long id) {
        bigliettoRepository.deleteById(id);
    }
}
