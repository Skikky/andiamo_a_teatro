package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Sala;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("ADMIN")
@RequestMapping("/sala")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @Secured("USER")
    @GetMapping("/get/{id}")
    public ResponseEntity<Sala> getSalaById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(salaService.getSalaById(id));
    }

    @Secured("USER")
    @GetMapping("/all")
    public ResponseEntity<List<Sala>> getAllSale() {
        return ResponseEntity.ok(salaService.getAllSale());
    }

    @PostMapping("/create")
    public ResponseEntity<Sala> createSala(@RequestBody Sala sala) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salaService.createSala(sala));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sala> updateSala(@PathVariable Long id, @RequestBody Sala newSala) throws EntityNotFoundException {
        return ResponseEntity.ok(salaService.updateSala(id, newSala));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSalaById(@PathVariable Long id) throws EntityNotFoundException {
        salaService.deleteSalaById(id);
    }
}
