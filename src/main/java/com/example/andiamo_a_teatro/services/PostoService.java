package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.PostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostoService {
    @Autowired
    private PostoRepository postoRepository;

    public Posto getPostiById(Long id) throws EntityNotFoundException {
        Optional<Posto> optionalPosto = postoRepository.findById(id);
        return optionalPosto.orElseThrow(() -> new EntityNotFoundException(id,"Posto"));
    }

    public List<Posto> getAllPosti() {
        return postoRepository.findAll();
    }

    public Posto createPosto(Posto posto) {
        return postoRepository.saveAndFlush(posto);
    }

    public Posto updatePosto(Long id, Posto newPosto) throws EntityNotFoundException {
        postoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Posto"));
        Posto posto = Posto.builder()
                .id(id)
                .fila(newPosto.getFila())
                .numero(newPosto.getNumero())
                .sala(newPosto.getSala())
                .build();
        postoRepository.saveAndFlush(posto);
        return posto;
    }

    public void deletePostoById(Long id) throws EntityNotFoundException {
        postoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Posto"));
        postoRepository.deleteById(id);
    }
}
