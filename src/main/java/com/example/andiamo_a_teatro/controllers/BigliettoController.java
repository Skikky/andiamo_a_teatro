package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.request.BigliettoRequest;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biglietto")
public class BigliettoController {
    @Autowired
    private BigliettoService bigliettoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<BigliettoResponse> getBigliettoById(@PathVariable Long id) {
        return ResponseEntity.ok(bigliettoService.getBigliettoById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BigliettoResponse>> getAllBiglietti() {
        return ResponseEntity.ok(bigliettoService.getAllBiglietti());
    }

    @PostMapping("/create")
    public ResponseEntity<BigliettoResponse> createBiglietto(@RequestBody BigliettoRequest bigliettoRequest) {
        return ResponseEntity.ok(bigliettoService.createBiglietto(bigliettoRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BigliettoResponse> updateBiglietto(@PathVariable Long id, @RequestBody BigliettoResponse bigliettoResponse) {
        return ResponseEntity.ok(bigliettoService.updateBiglietto(id, bigliettoResponse));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBigliettoById(@PathVariable Long id) {
        bigliettoService.deleteBigliettoById(id);
    }
}
