package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Genere;
import com.example.andiamo_a_teatro.entities.Sala;
import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.exception.NoSpettacoliFoundException;
import com.example.andiamo_a_teatro.repositories.SpettacoloRepository;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.UtenteRepository;
import com.example.andiamo_a_teatro.request.SpettacoloRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpettacoloService {
    @Autowired
    private SpettacoloRepository spettacoloRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SalaService salaService;
    @Autowired
    private GenereService genereService;

    private Sala mapToSala(Long salaId) throws EntityNotFoundException {
        return salaService.getSalaById(salaId);
    }

    private Genere mapToGenere(Long genereId) throws EntityNotFoundException {
        return genereService.getGenereById(genereId);
    }

    public List<Spettacolo> searchSpettacoli(Long idGenere, Long idComune, Boolean isOpen, LocalDateTime dataInizio, LocalDateTime dataFine) throws NoSpettacoliFoundException {
        Specification<Spettacolo> spec = Specification.where(hasGenere(idGenere))
                .and(inComune(idComune))
                .and(isOpen(isOpen))
                .and(betweenDates(dataInizio, dataFine));

        List<Spettacolo> spettacoli = spettacoloRepository.findAll(spec);
        if (spettacoli.isEmpty()) {
            throw new NoSpettacoliFoundException();
        }
        return spettacoloRepository.findAll(spec);
    }

    private Specification<Spettacolo> hasGenere(Long idGenere) {
        return (root, query, criteriaBuilder) -> idGenere == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.join("genere").get("id"), idGenere);
    }

    private Specification<Spettacolo> inComune(Long idComune) {
        return (root, query, criteriaBuilder) -> idComune == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.join("sala").join("sede").join("comune").get("id"), idComune);
    }

    private Specification<Spettacolo> isOpen(Boolean isOpen) {
        return (root, query, criteriaBuilder) -> isOpen == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.join("sala").join("sede").get("isOpen"), isOpen);
    }

    private Specification<Spettacolo> betweenDates(LocalDateTime dataInizio, LocalDateTime dataFine) {
        return (root, query, criteriaBuilder) -> {
            if (dataInizio == null && dataFine == null) {
                return criteriaBuilder.conjunction();
            } else if (dataInizio != null && dataFine == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("orario"), dataInizio);
            } else if (dataInizio == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("orario"), dataFine);
            } else {
                return criteriaBuilder.between(root.get("orario"), dataInizio, dataFine);
            }
        };
    }

    public Spettacolo getSpettacoloById(Long id) throws EntityNotFoundException {
        Optional<Spettacolo> optionalSpettacolo = spettacoloRepository.findById(id);
        return optionalSpettacolo.orElseThrow(() -> new EntityNotFoundException(id, "Spettacolo"));
    }

    public List<Spettacolo> getAllSpettacoli() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo createSpettacolo(SpettacoloRequest spettacoloRequest) throws EntityNotFoundException {
        Spettacolo spettacolo = Spettacolo.builder()
                .nome(spettacoloRequest.getNome())
                .orario(spettacoloRequest.getOrario())
                .durata(spettacoloRequest.getDurata())
                .prezzo(spettacoloRequest.getPrezzo())
                .sala(mapToSala(spettacoloRequest.getId_sala()))
                .genere(mapToGenere(spettacoloRequest.getId_genere()))
                .build();
        notifyUsers(spettacolo);
        return spettacoloRepository.saveAndFlush(spettacolo);
    }

    public Spettacolo updateSpettacolo(Long id, SpettacoloRequest spettacoloRequest) throws EntityNotFoundException {
        getSpettacoloById(id);
        Spettacolo spettacolo = Spettacolo.builder()
                .id(id)
                .nome(spettacoloRequest.getNome())
                .orario(spettacoloRequest.getOrario())
                .durata(spettacoloRequest.getDurata())
                .prezzo(spettacoloRequest.getPrezzo())
                .sala(mapToSala(spettacoloRequest.getId_sala()))
                .genere(mapToGenere(spettacoloRequest.getId_genere()))
                .build();
        spettacoloRepository.saveAndFlush(spettacolo);
        return spettacolo;
    }

    public void deleteSpettacoloById(Long id) throws EntityNotFoundException {
        getSpettacoloById(id);
        spettacoloRepository.deleteById(id);
    }

    private void notifyUsers(Spettacolo spettacolo) {
        Long comuneId = spettacolo.getSala().getSede().getComune().getId();
        List<Utente> users = utenteRepository.findAllByComuneId(comuneId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Nuovo Spettacolo Disponibile");

        users.forEach(user -> {
            message.setTo(user.getEmail());
            message.setText("Ciao " + user.getNome() + ", un nuovo spettacolo è disponibile nella tua città: " + spettacolo.getNome());
            javaMailSender.send(message);
        });
    }
}
