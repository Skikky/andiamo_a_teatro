package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.repositories.BigliettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BigliettoService {
    @Autowired
    private BigliettoRepository bigliettoRepository;

    public Biglietto getBigliettoById(Long id) {
        Optional<Biglietto> optionalBiglietto = bigliettoRepository.findById(id);
        return optionalBiglietto.orElseThrow(() -> new RuntimeException("Biglietto non trovato con id: " + id));
    }

    public List<Biglietto> getAllBiglietti() {
        return bigliettoRepository.findAll();
    }

    public Biglietto createBiglietto(Biglietto biglietto) {
        return bigliettoRepository.saveAndFlush(biglietto);
    }

    public Biglietto updateBiglietto(Long id, Biglietto newBiglietto) {
        Biglietto biglietto = Biglietto.builder()
                .id(id)
                .timestamp(newBiglietto.getTimestamp())
                .utente(newBiglietto.getUtente())
                .spettacolo(newBiglietto.getSpettacolo())
                .posto(newBiglietto.getPosto())
                .build();
        bigliettoRepository.saveAndFlush(biglietto);
        return biglietto;
    }

    public void deleteBigliettoById(Long id) {
        bigliettoRepository.deleteById(id);
    }
}
