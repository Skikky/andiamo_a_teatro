package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Posto;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.request.PostoRequest;
import com.example.andiamo_a_teatro.services.PostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({"SUPERADMIN", "ADMIN"})
@RequestMapping("/posto")
public class PostoController {
    @Autowired
    private PostoService postoService;

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/get/{id}")
    public ResponseEntity<Posto> getPostoById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(postoService.getPostiById(id));
    }

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/all")
    public ResponseEntity<List<Posto>> getAllPosti() {
        return ResponseEntity.ok(postoService.getAllPosti());
    }

    @PostMapping("/create")
    public ResponseEntity<Posto> createPosto(@RequestBody PostoRequest postoRequest) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(postoService.createPosto(postoRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Posto> updatePosto(@PathVariable Long id, @RequestBody PostoRequest postoRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(postoService.updatePosto(id, postoRequest));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePostoById(@PathVariable Long id) throws EntityNotFoundException {
        postoService.deletePostoById(id);
    }
}
