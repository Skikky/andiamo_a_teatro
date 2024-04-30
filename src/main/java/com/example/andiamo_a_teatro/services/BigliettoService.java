package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
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

    private Utente mapToUtente(Long utenteId) throws EntityNotFoundException {
        return utenteRepository.findById(utenteId)
                .orElseThrow(() -> new EntityNotFoundException(utenteId, "Utente"));
    }

    private Spettacolo mapToSpettacolo(Long spettacoloId) throws EntityNotFoundException {
        return spettacoloRepository.findById(spettacoloId)
                .orElseThrow(() -> new EntityNotFoundException(spettacoloId, "Spettacolo"));
    }

    private Posto mapToPosto(Long postoId) throws EntityNotFoundException {
        return postoRepository.findById(postoId)
                .orElseThrow(() -> new EntityNotFoundException(postoId, "Posto"));
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

    public BigliettoResponse getBigliettoById(Long id) throws EntityNotFoundException {
        Biglietto biglietto = bigliettoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Biglietto"));
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

    public BigliettoResponse createBiglietto(BigliettoRequest bigliettoRequest) throws EntityNotFoundException{
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

    public BigliettoResponse updateBiglietto(Long id, BigliettoResponse bigliettoResponse) throws EntityNotFoundException {

        bigliettoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Biglietto"));

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

    public void deleteBigliettoById(Long id) throws EntityNotFoundException {
        bigliettoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Biglietto"));
        bigliettoRepository.deleteById(id);
    }
}
