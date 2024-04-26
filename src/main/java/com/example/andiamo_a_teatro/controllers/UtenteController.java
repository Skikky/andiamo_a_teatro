package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Biglietto;
import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.response.UtenteResponse;
import com.example.andiamo_a_teatro.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/{utenteId}/acquista-biglietto/{bigliettoId}")
    public ResponseEntity<?> acquistaBiglietto(@PathVariable Long utenteId, @PathVariable Long bigliettoId) {
        try {
            BigliettoResponse biglietto = utenteService.acquistaBiglietto(utenteId, bigliettoId);
            return ResponseEntity.ok("Biglietto acquistato con successo per l'utente con ID: " + utenteId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Errore durante l'acquisto del biglietto: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UtenteResponse> getUtenteById(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UtenteResponse>> getAllUtenti() {
        return ResponseEntity.ok(utenteService.getAllUtenti());
    }

    @PostMapping("/create")
    public ResponseEntity<UtenteResponse> createUtente(@RequestBody Utente utente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(utenteService.createUtente(utente));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UtenteResponse> updateUtente(@PathVariable Long id, @RequestBody Utente newUtente) {
        return ResponseEntity.ok(utenteService.updateUtente(id, newUtente));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUtenteById(@PathVariable Long id) {
        utenteService.deleteUtenteById(id);
    }
}
