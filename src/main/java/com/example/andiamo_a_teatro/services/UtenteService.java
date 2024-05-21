package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.*;
import com.example.andiamo_a_teatro.enums.Role;
import com.example.andiamo_a_teatro.exception.*;
import com.example.andiamo_a_teatro.repositories.*;
import com.example.andiamo_a_teatro.request.NewsRequest;
import com.example.andiamo_a_teatro.request.RecensioneRequest;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.response.GenericResponse;
import com.example.andiamo_a_teatro.response.RecensioneResponse;
import com.example.andiamo_a_teatro.response.UtenteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    private NewsService newsService;

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
        Utente utente = getUtenteById(utenteId);

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

    public Utente getUtenteById(Long id) throws EntityNotFoundException {
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id,"Utente"));
        return utente;
    }

    public UtenteResponse getUtenteResponseById(Long id) throws EntityNotFoundException {
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

    public RecensioneResponse scriviRecensione(Long utenteId, RecensioneRequest recensioneRequest) throws SpettacoloNonVistoException, EntityNotFoundException {
        Utente utente = getUtenteById(utenteId);

        Spettacolo spettacolo = spettacoloService.getSpettacoloById(recensioneRequest.getId_spettacolo());

        boolean visto = utente.getBigliettiUtente().stream()
                .anyMatch(biglietto -> biglietto.getSpettacolo().getId().equals(recensioneRequest.getId_spettacolo())
                        && biglietto.getSpettacolo().getOrario().isBefore(LocalDateTime.now()));

        if (!visto) {
            throw new SpettacoloNonVistoException();
        }

        Recensione recensione = Recensione.builder()
                .testo(recensioneRequest.getTesto())
                .voto(recensioneRequest.getVoto())
                .spettacolo(spettacolo)
                .utente(utente)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        recensioneRepository.saveAndFlush(recensione);
        return mapToRecensioneResponse(recensione);
    }

    public void updateRole(Long id, String new_role) throws EntityNotFoundException {
        Utente utente = getUtenteById(id);
        utente.setRole(Role.valueOf(new_role));
        utenteRepository.saveAndFlush(utente);
    }

    @Transactional
    public GenericResponse addLike(Long newsId, Long userId) throws EntityNotFoundException , LikePresente{
        News news = newsService.getNewsById(newsId);
        Utente utente = getUtenteById(userId);

        if (!news.getLikedByUsers().contains(utente)) {
            news.getLikedByUsers().add(utente);
            news.setLikes(news.getLikes()+1);

            NewsRequest newsRequest = NewsRequest.builder()
                    .body(news.getBody())
                    .title(news.getTitle())
                    .build();
            newsService.updateNews(newsId, newsRequest);
            return new GenericResponse("like aggiunto con successo!");
        } else {
            throw new LikePresente(userId);
        }
    }

    @Transactional
    public GenericResponse removeLike(Long userId, Long newsId) throws EntityNotFoundException, LikeAssente {
        News news = newsService.getNewsById(newsId);
        Utente utente = getUtenteById(userId);

        if (news.getLikedByUsers().contains(utente)) {
            news.getLikedByUsers().remove(utente);
            news.setLikes(news.getLikes()-1);

            NewsRequest newsRequest = NewsRequest.builder()
                    .body(news.getBody())
                    .title(news.getTitle())
                    .build();
            newsService.updateNews(newsId, newsRequest);
            return new GenericResponse("Like rimosso con successo!");
        } else {
            throw new LikeAssente(userId);
        }
    }
}
