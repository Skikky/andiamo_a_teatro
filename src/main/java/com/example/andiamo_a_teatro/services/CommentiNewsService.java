package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.CommentiNews;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.CommentiNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentiNewsService {
    @Autowired
    private CommentiNewsRepository commentiNewsRepository;

    public CommentiNews getCommentoById(Long id) throws EntityNotFoundException {
        Optional<CommentiNews> optionalCommentiNews = commentiNewsRepository.findById(id);
        return optionalCommentiNews.orElseThrow(() -> new EntityNotFoundException(id,"CommentiNews"));
    }

    public List<CommentiNews> getAllCommenti() {
        return commentiNewsRepository.findAll();
    }

    public CommentiNews createCommento(CommentiNews commentiNews) {
        commentiNews.setInsertTime(LocalDateTime.now());
        return commentiNewsRepository.saveAndFlush(commentiNews);
    }

    public CommentiNews updateCommento (Long id, String testo) throws EntityNotFoundException {
        CommentiNews commentiNews = getCommentoById(id);
        commentiNews.setTesto(testo);
        commentiNews.setLastUpdate(LocalDateTime.now());
        return commentiNewsRepository.saveAndFlush(commentiNews);
    }

    public void deleteCommento(Long id) throws EntityNotFoundException {
        getCommentoById(id);
        commentiNewsRepository.deleteById(id);
    }
}

