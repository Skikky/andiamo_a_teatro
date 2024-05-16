package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.response.RecensioneResponse;
import com.example.andiamo_a_teatro.services.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({"SUPERADMIN","ADMIN","USER"})
@RequestMapping("/recensione")
public class RecensioneController {
    @Autowired
    private RecensioneService recensioneService;

    @GetMapping("/get/{id}")
    public ResponseEntity<RecensioneResponse> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(recensioneService.getRecensioneById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecensioneResponse>> getAllPosti() {
        return ResponseEntity.ok(recensioneService.getAllRecensioni());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RecensioneResponse> updatePosto(@PathVariable Long id, @RequestParam String testo, @RequestParam Integer voto) throws EntityNotFoundException {
        return ResponseEntity.ok(recensioneService.updateRecensione(id, testo, voto));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePostoById(@PathVariable Long id) throws EntityNotFoundException {
        recensioneService.deleteRecensioneById(id);
    }
}
