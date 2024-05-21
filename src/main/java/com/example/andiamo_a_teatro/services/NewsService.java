package com.example.andiamo_a_teatro.services;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.repositories.NewsRepository;
import com.example.andiamo_a_teatro.request.NewsRequest;
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

    public News createNews(NewsRequest newsRequest) {
        News news = News.builder()
                .body(newsRequest.getBody())
                .title(newsRequest.getTitle())
                .likes(0)
                .likedByUsers(null)
                .build();
        return newsRepository.saveAndFlush(news);
    }

    public News updateNews(Long id, NewsRequest newsRequest) throws EntityNotFoundException {
        News existingNews = getNewsById(id);
        News news = News.builder()
                .id(id)
                .title(newsRequest.getTitle())
                .body(newsRequest.getBody())
                .likes(existingNews.getLikes())
                .likedByUsers(existingNews.getLikedByUsers())
                .build();
        return newsRepository.saveAndFlush(news);
    }

    public void deleteNewsById(Long id) throws EntityNotFoundException {
        getNewsById(id);
        newsRepository.deleteById(id);
    }
}
