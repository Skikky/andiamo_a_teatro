package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.request.BigliettoRequest;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({"SUPERADMIN","ADMIN","USER"})
@RequestMapping("/biglietto")
public class BigliettoController {
    @Autowired
    private BigliettoService bigliettoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<BigliettoResponse> getBigliettoById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(bigliettoService.getBigliettoById(id));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<BigliettoResponse>> getAllBiglietti() {
        return ResponseEntity.ok(bigliettoService.getAllBiglietti());
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<BigliettoResponse> createBiglietto(@RequestBody BigliettoRequest bigliettoRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(bigliettoService.createBiglietto(bigliettoRequest));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<BigliettoResponse> updateBiglietto(@PathVariable Long id, @RequestBody BigliettoResponse bigliettoResponse) throws EntityNotFoundException {
        return ResponseEntity.ok(bigliettoService.updateBiglietto(id, bigliettoResponse));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @DeleteMapping("/delete/{id}")
    public void deleteBigliettoById(@PathVariable Long id) throws  EntityNotFoundException {
        bigliettoService.deleteBigliettoById(id);
    }
}
