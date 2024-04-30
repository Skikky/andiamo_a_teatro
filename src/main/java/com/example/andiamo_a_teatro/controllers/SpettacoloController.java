package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.exception.NoSpettacoliFoundException;
import com.example.andiamo_a_teatro.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("spettacolo")
public class SpettacoloController {
    @Autowired
    private SpettacoloService spettacoloService;

    @GetMapping("/ricerca")
    public ResponseEntity<List<Spettacolo>> searchSpettacoli(
            @RequestParam(value = "idGenere", required = false) Long idGenere,
            @RequestParam(value = "idComune", required = false) Long idComune,
            @RequestParam(value = "isOpen", required = false) Boolean isOpen,
            @RequestParam(value = "dataInizio", required = false) String dataInizio,
            @RequestParam(value = "dataFine", required = false) String dataFine) throws NoSpettacoliFoundException {

        LocalDateTime start = dataInizio != null ? LocalDateTime.parse(dataInizio) : null;
        LocalDateTime end = dataFine != null ? LocalDateTime.parse(dataFine) : null;

        List<Spettacolo> spettacoli = spettacoloService.searchSpettacoli(idGenere, idComune, isOpen, start, end);
        return ResponseEntity.ok(spettacoli);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Spettacolo> getSpettacoloById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(spettacoloService.getSpettacoloById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Spettacolo>> getAllSpettacoli() {
        return ResponseEntity.ok(spettacoloService.getAllSpettacoli());
    }

    @PostMapping("/create")
    public ResponseEntity<Spettacolo> createSpettacolo(@RequestBody Spettacolo spettacolo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spettacoloService.createSpettacolo(spettacolo));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Spettacolo> updateSpettacolo(@PathVariable Long id, @RequestBody Spettacolo newSpettacolo) throws EntityNotFoundException {
        return ResponseEntity.ok(spettacoloService.updateSpettacolo(id, newSpettacolo));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSpettacoloById(@PathVariable Long id) throws EntityNotFoundException {
        spettacoloService.deleteSpettacoloById(id);
    }
}
