package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Sede;
import com.example.andiamo_a_teatro.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sede")
public class SedeController {
    @Autowired
    private SedeService sedeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Sede> getSedeById(@PathVariable Long id) {
        return ResponseEntity.ok(sedeService.getSedeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sede>> getAllSedi() {
        return ResponseEntity.ok(sedeService.getAllSedi());
    }

    @PostMapping("/create")
    public ResponseEntity<Sede> createSede(@RequestBody Sede sede) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sedeService.createSede(sede));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sede> updateSede(@PathVariable Long id, @RequestBody Sede newSede) {
        return ResponseEntity.ok(sedeService.updateSede(id, newSede));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSedeById(@PathVariable Long id) {
        sedeService.deleteSedeById(id);
    }
}
