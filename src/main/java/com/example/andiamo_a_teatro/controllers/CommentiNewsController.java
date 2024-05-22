package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.CommentiNews;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.response.GenericResponse;
import com.example.andiamo_a_teatro.services.CommentiNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentiNewsController {

    @Autowired
    private CommentiNewsService commentiNewsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentiNews> getCommentoNewsById(@PathVariable Long id) throws EntityNotFoundException {
        return new ResponseEntity<>(commentiNewsService.getCommentoById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentiNews>> getAllCommentiNews() {
        return new ResponseEntity<>(commentiNewsService.getAllCommenti(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCommentoNews(@RequestBody CommentiNews commentiNews) {
        return new ResponseEntity<>(commentiNewsService.createCommento(commentiNews), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentiNews> updateCommento(@PathVariable Long id, @RequestParam String newTesto) throws EntityNotFoundException {
        return new ResponseEntity<>(commentiNewsService.updateCommento(id, newTesto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteCommentoNews(@PathVariable Long id) throws EntityNotFoundException {
        commentiNewsService.deleteCommento(id);
        return new ResponseEntity<>(new GenericResponse("Commento con id " + id + " eliminato con successo"), HttpStatus.OK);
    }


}
