package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Genere;
import com.example.andiamo_a_teatro.services.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genere")
public class GenereController {
    @Autowired
    private GenereService genereService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Genere> getGenereById(@PathVariable Long id) {
        return ResponseEntity.ok(genereService.getGenereById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genere>> getAllGeneri() {
        return ResponseEntity.ok(genereService.getAllGeneri());
    }

    @PostMapping("/create")
    public ResponseEntity<Genere> createGenere(@RequestBody Genere genere) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genereService.createGenere(genere));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Genere> updateGenere(@PathVariable Long id, @RequestBody Genere newGenere) {
        return ResponseEntity.ok(genereService.updateGenere(id, newGenere));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGenereById(@PathVariable Long id) {
        genereService.deleteGenereById(id);
    }
}
