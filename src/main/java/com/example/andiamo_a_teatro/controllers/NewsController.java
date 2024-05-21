package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({"SUPERADMIN","ADMIN","USER"})
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<News> getNewsResponseById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @Secured({"SUPERADMIN","ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return new ResponseEntity<>(newsService.createNews(news), HttpStatus.CREATED);
    }

    @Secured({"SUPERADMIN","ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<News> updateNews(@RequestBody News newsRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(newsService.updateNews(newsRequest));
    }

    @Secured({"SUPERADMIN","ADMIN"})
    @DeleteMapping("/delete/{id}")
    public void deleteNewsById(@PathVariable Long id) throws EntityNotFoundException {
        newsService.deleteNewsById(id);
    }
}
