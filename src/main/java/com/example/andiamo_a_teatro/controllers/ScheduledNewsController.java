package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.News;
import com.example.andiamo_a_teatro.entities.ScheduledNews;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.request.ScheduledNewsRequest;
import com.example.andiamo_a_teatro.services.ScheduledNewsService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduled_news")
public class ScheduledNewsController {
    @Autowired
    private ScheduledNewsService scheduledNewsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ScheduledNews> getNewsById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(scheduledNewsService.getScheduledNewsById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduledNews>> getAllScheduledNews() {
        return new ResponseEntity<>(scheduledNewsService.getAllScheduledNews(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ScheduledNewsRequest> createScheduledNews(@RequestBody ScheduledNewsRequest request) throws SchedulerException {
        return new ResponseEntity<>(scheduledNewsService.createScheduledNews(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduledNewsRequest> updateScheduledNews(@PathVariable Long id, @RequestBody ScheduledNewsRequest request) throws SchedulerException {
        return new ResponseEntity<>(scheduledNewsService.updateScheduledNews(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteScheduledNewsById(@PathVariable Long id) throws SchedulerException {
        scheduledNewsService.deleteScheduledNewsById(id);
    }
}
