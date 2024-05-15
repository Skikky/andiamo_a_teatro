package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Genere;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenereService {
    @Autowired
    private GenereRepository genereRepository;

    public Genere getGenereById(Long id) throws EntityNotFoundException {
        Optional<Genere> optionalGenere = genereRepository.findById(id);
        return optionalGenere.orElseThrow(() -> new EntityNotFoundException(id,"Genere"));
    }

    public List<Genere> getAllGeneri() {
        return genereRepository.findAll();
    }

    public Genere createGenere(Genere genere) {
        return genereRepository.saveAndFlush(genere);
    }

    public Genere updateGenere(Long id, Genere newGenere) throws EntityNotFoundException {
        getGenereById(id);
        Genere genere = Genere.builder()
                .id(id)
                .nome(newGenere.getNome())
                .build();
        genereRepository.saveAndFlush(genere);
        return genere;
    }

    public void deleteGenereById(Long id) throws EntityNotFoundException {
        getGenereById(id);
        genereRepository.deleteById(id);
    }
}
