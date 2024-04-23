package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biglietto")
public class BigliettoController {
    @Autowired
    private BigliettoService bigliettoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Biglietto> getBigliettoById(@PathVariable Long id) {
        return ResponseEntity.ok(bigliettoService.getBigliettoById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Biglietto>> getAllBiglietti() {
        return ResponseEntity.ok(bigliettoService.getAllBiglietti());
    }

    @PostMapping("/create")
    public ResponseEntity<Biglietto> createBiglietto(@RequestBody Biglietto biglietto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bigliettoService.createBiglietto(biglietto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Biglietto> updateBiglietto(@PathVariable Long id, @RequestBody Biglietto newBiglietto) {
        return ResponseEntity.ok(bigliettoService.updateBiglietto(id, newBiglietto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBigliettoById(@PathVariable Long id) {
        bigliettoService.deleteBigliettoById(id);
    }
}
