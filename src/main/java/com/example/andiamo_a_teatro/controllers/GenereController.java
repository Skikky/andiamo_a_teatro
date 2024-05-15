package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Genere;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("SUPERADMIN")
@RequestMapping("/genere")
public class GenereController {
    @Autowired
    private GenereService genereService;

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/get/{id}")
    public ResponseEntity<Genere> getGenereById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(genereService.getGenereById(id));
    }

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/all")
    public ResponseEntity<List<Genere>> getAllGeneri() {
        return ResponseEntity.ok(genereService.getAllGeneri());
    }

    @PostMapping("/create")
    public ResponseEntity<Genere> createGenere(@RequestBody Genere genere) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genereService.createGenere(genere));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Genere> updateGenere(@PathVariable Long id, @RequestBody Genere newGenere) throws EntityNotFoundException {
        return ResponseEntity.ok(genereService.updateGenere(id, newGenere));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGenereById(@PathVariable Long id) throws EntityNotFoundException {
        genereService.deleteGenereById(id);
    }
}
