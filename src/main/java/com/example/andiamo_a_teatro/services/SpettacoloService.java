package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.repositories.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpettacoloService {
    @Autowired
    private SpettacoloRepository spettacoloRepository;

    public List<Spettacolo> searchSpettacoli(Long idGenere, Long idComune, Boolean isOpen, LocalDateTime dataInizio, LocalDateTime dataFine) {
        Specification<Spettacolo> spec = Specification.where(hasGenere(idGenere))
                .and(inComune(idComune))
                .and(isOpen(isOpen))
                .and(betweenDates(dataInizio, dataFine));
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

    public Spettacolo getSpettacoloById(Long id) {
        Optional<Spettacolo> optionalSpettacolo = spettacoloRepository.findById(id);
        return optionalSpettacolo.orElseThrow(() -> new RuntimeException("Spettacolo non trovato con id: " + id));
    }

    public List<Spettacolo> getAllSpettacoli() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo createSpettacolo(Spettacolo spettacolo) {
        return spettacoloRepository.saveAndFlush(spettacolo);
    }

    public Spettacolo updateSpettacolo(Long id, Spettacolo newSpettacolo) {
        Spettacolo spettacolo = Spettacolo.builder()
                .id(id)
                .nome(newSpettacolo.getNome())
                .orario(newSpettacolo.getOrario())
                .durata(newSpettacolo.getDurata())
                .prezzo(newSpettacolo.getPrezzo())
                .sala(newSpettacolo.getSala())
                .genere(newSpettacolo.getGenere())
                .build();
        spettacoloRepository.saveAndFlush(spettacolo);
        return spettacolo;
    }

    public void deleteSpettacoloById(Long id) {
        spettacoloRepository.deleteById(id);
    }
}
