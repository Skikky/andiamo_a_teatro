package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Comune;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.services.ComuneService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("SUPERADMIN")
@RequestMapping("comune")
public class ComuneController {
    @Autowired
    private ComuneService comuneService;

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/get/{id}")
    public ResponseEntity<Comune> getComuneById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(comuneService.getComuneById(id));
    }

    @Secured({"SUPERADMIN","ADMIN","USER"})
    @GetMapping("/all")
    public ResponseEntity<List<Comune>> getAllComuni() {
        return ResponseEntity.ok(comuneService.getAllComuni());
    }

    @PostMapping("/create")
    public ResponseEntity<Comune> createComune(@RequestBody Comune comune) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comuneService.createComune(comune));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Comune> updateComune(@PathVariable Long id, @RequestBody Comune newComune) throws EntityNotFoundException {
        return ResponseEntity.ok(comuneService.updateComune(id, newComune));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComuneById(@PathVariable Long id) throws EntityNotFoundException {
        comuneService.deleteComuneById(id);
    }
}
