package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.NewsRepository;
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
        return optionalNews.orElseThrow(() -> new EntityNotFoundException(id,"News"));
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News createNews(News News) {
        newsRepository.saveAndFlush(News);
        return News;
    }

    public News updateNews(News newsRequest) throws EntityNotFoundException {
        getNewsById(newsRequest.getId());
        News news = News.builder()
                .id(newsRequest.getId())
                .title(newsRequest.getTitle())
                .body(newsRequest.getBody())
                .build();
        newsRepository.saveAndFlush(news);
        return news;
    }

    public void deleteNewsById(Long id) throws EntityNotFoundException {
        getNewsById(id);
        newsRepository.deleteById(id);
    }
}
