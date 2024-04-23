package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spettacolo")
public class SpettacoloController {
    @Autowired
    private SpettacoloService spettacoloService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Spettacolo> getSpettacoloById(@PathVariable Long id) {
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
    public ResponseEntity<Spettacolo> updateSpettacolo(@PathVariable Long id, @RequestBody Spettacolo newSpettacolo) {
        return ResponseEntity.ok(spettacoloService.updateSpettacolo(id, newSpettacolo));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSpettacoloById(@PathVariable Long id) {
        spettacoloService.deleteSpettacoloById(id);
    }
}
