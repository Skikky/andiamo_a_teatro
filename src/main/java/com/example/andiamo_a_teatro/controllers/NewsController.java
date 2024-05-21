package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<News> createNews(@RequestBody News News) {
        return new ResponseEntity<>(newsService.createNews(News), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<News> updateNews(@RequestBody News newsRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(newsService.updateNews(newsRequest));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteNewsById(@PathVariable Long id) throws EntityNotFoundException {
        newsService.deleteNewsById(id);
    }
}
