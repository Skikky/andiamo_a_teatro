package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.services.PostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posto")
public class PostoController {
    @Autowired
    private PostoService postoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Posto> getPostoById(@PathVariable Long id) {
        return ResponseEntity.ok(postoService.getPostiById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Posto>> getAllPosti() {
        return ResponseEntity.ok(postoService.getAllPosti());
    }

    @PostMapping("/create")
    public ResponseEntity<Posto> createPosto(@RequestBody Posto posto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postoService.createPosto(posto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Posto> updatePosto(@PathVariable Long id, @RequestBody Posto newPosto) {
        return ResponseEntity.ok(postoService.updatePosto(id, newPosto));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePostoById(@PathVariable Long id) {
        postoService.deletePostoById(id);
    }
}
