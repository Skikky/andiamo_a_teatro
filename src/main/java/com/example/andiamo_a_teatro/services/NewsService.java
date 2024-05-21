package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.NewsRepository;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public News getNewsById(Long id) throws EntityNotFoundException {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (optionalNews.isEmpty()) throw new EntityNotFoundException();
        return optionalNews.get();
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News createNews(News News) {
        newsRepository.saveAndFlush(News);
        return News;
    }
}
