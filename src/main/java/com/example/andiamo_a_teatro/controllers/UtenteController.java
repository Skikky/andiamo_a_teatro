package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Utente;
import com.example.andiamo_a_teatro.exception.*;
import com.example.andiamo_a_teatro.request.RecensioneRequest;
import com.example.andiamo_a_teatro.response.BigliettoResponse;
import com.example.andiamo_a_teatro.response.GenericResponse;
import com.example.andiamo_a_teatro.response.RecensioneResponse;
import com.example.andiamo_a_teatro.response.UtenteResponse;
import com.example.andiamo_a_teatro.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured({"SUPERADMIN","ADMIN","USER"})
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/acquista-biglietto")
    public ResponseEntity<?> acquistaBiglietto(@RequestParam Long utenteId, @RequestParam Long bigliettoId) throws PoveroException, BigliettoNonDisponibileException, EntityNotFoundException {
        try {
            BigliettoResponse biglietto = utenteService.acquistaBiglietto(utenteId, bigliettoId);
            return ResponseEntity.ok("Biglietto acquistato con successo per l'utente con ID: " + utenteId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Errore durante l'acquisto del biglietto: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UtenteResponse> getUtenteResponseById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(utenteService.getUtenteResponseById(id));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<UtenteResponse>> getAllUtenti() {
        return ResponseEntity.ok(utenteService.getAllUtenti());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UtenteResponse> updateUtente(@PathVariable Long id, @RequestBody Utente newUtente) {
        return ResponseEntity.ok(utenteService.updateUtente(id, newUtente));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUtenteById(@PathVariable Long id) throws EntityNotFoundException {
        utenteService.deleteUtenteById(id);
    }

    @PostMapping("/scriviRecensione/{utenteId}")
    public ResponseEntity<?> scriviRecensione(@PathVariable Long utenteId, @RequestBody RecensioneRequest recensioneRequest) {
        try {
            RecensioneResponse recensioneResponse = utenteService.scriviRecensione(utenteId, recensioneRequest);
            return ResponseEntity.ok(recensioneResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore nella creazione della recensione: " + e.getMessage());
        }
    }

    @Secured("SUPERADMIN")
    @PutMapping("/update/role")
    public ResponseEntity<String> updateRole(@RequestParam Long id, @RequestParam String new_role) throws EntityNotFoundException {
        utenteService.updateRole(id, new_role);
        return new ResponseEntity<>("Ruolo aggiornato con successo", HttpStatus.CREATED);
    }

    @PostMapping("/like/{userId}")
    public ResponseEntity<?> addLike(@PathVariable Long userId, @RequestParam Long newsId) throws EntityNotFoundException {
        try {
            GenericResponse response = utenteService.addLike(newsId,userId);
            return ResponseEntity.ok(response);
        } catch (LikePresente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/unlike/{userId}")
    public ResponseEntity<?> removeLike(@PathVariable Long userId, @RequestParam Long newsId) throws EntityNotFoundException {
        try {
            GenericResponse response = utenteService.removeLike(userId, newsId);
            return ResponseEntity.ok(response);
        } catch (LikeAssente e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
