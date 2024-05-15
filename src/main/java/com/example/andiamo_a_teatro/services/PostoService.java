package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.entities.Sala;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.PostoRepository;
import com.example.andiamo_a_teatro.request.PostoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostoService {
    @Autowired
    private PostoRepository postoRepository;
    @Autowired
    private SalaService salaService;

    private Sala mapToSala(Long salaId) throws EntityNotFoundException {
        return salaService.getSalaById(salaId);
    }

    public Posto getPostiById(Long id) throws EntityNotFoundException {
        Optional<Posto> optionalPosto = postoRepository.findById(id);
        return optionalPosto.orElseThrow(() -> new EntityNotFoundException(id,"Posto"));
    }

    public List<Posto> getAllPosti() {
        return postoRepository.findAll();
    }

    public Posto createPosto(PostoRequest postoRequest) throws EntityNotFoundException {
        Posto posto = Posto.builder()
                .fila(postoRequest.getFila())
                .numero(postoRequest.getNumero())
                .sala(mapToSala(postoRequest.getId_sala()))
                .build();
        return postoRepository.saveAndFlush(posto);
    }

    public Posto updatePosto(Long id, PostoRequest postoRequest) throws EntityNotFoundException {
        getPostiById(id);
        Posto posto = Posto.builder()
                .id(id)
                .fila(postoRequest.getFila())
                .numero(postoRequest.getNumero())
                .sala(mapToSala(postoRequest.getId_sala()))
                .build();
        postoRepository.saveAndFlush(posto);
        return posto;
    }

    public void deletePostoById(Long id) throws EntityNotFoundException {
        getPostiById(id);
        postoRepository.deleteById(id);
    }
}
