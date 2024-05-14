package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Sede;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("USER")
@RequestMapping("/sede")
public class SedeController {
    @Autowired
    private SedeService sedeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Sede> getSedeById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(sedeService.getSedeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sede>> getAllSedi() {
        return ResponseEntity.ok(sedeService.getAllSedi());
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<Sede> createSede(@RequestBody Sede sede) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sedeService.createSede(sede));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<Sede> updateSede(@PathVariable Long id, @RequestBody Sede newSede) throws EntityNotFoundException{
        return ResponseEntity.ok(sedeService.updateSede(id, newSede));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @DeleteMapping("/delete/{id}")
    public void deleteSedeById(@PathVariable Long id) throws EntityNotFoundException{
        sedeService.deleteSedeById(id);
    }
}
