package com.example.andiamo_a_teatro.controllers;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import com.example.andiamo_a_teatro.exception.EntityNotFoundException;
import com.example.andiamo_a_teatro.exception.ErrorResponse;
import com.example.andiamo_a_teatro.exception.NoSpettacoliFoundException;
import com.example.andiamo_a_teatro.request.SpettacoloRequest;
import com.example.andiamo_a_teatro.response.GenericResponse;
import com.example.andiamo_a_teatro.services.SpettacoloService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("spettacolo")
@Secured({"SUPERADMIN","ADMIN","USER"})
public class SpettacoloController {
    @Autowired
    private SpettacoloService spettacoloService;

    @GetMapping("/ricerca")
    public ResponseEntity<List<Spettacolo>> searchSpettacoli(
            @RequestParam(value = "idGenere", required = false) Long idGenere,
            @RequestParam(value = "idComune", required = false) Long idComune,
            @RequestParam(value = "isOpen", required = false) Boolean isOpen,
            @RequestParam(value = "dataInizio", required = false) String dataInizio,
            @RequestParam(value = "dataFine", required = false) String dataFine) throws NoSpettacoliFoundException {

        LocalDateTime start = dataInizio != null ? LocalDateTime.parse(dataInizio) : null;
        LocalDateTime end = dataFine != null ? LocalDateTime.parse(dataFine) : null;

        List<Spettacolo> spettacoli = spettacoloService.searchSpettacoli(idGenere, idComune, isOpen, start, end);
        return ResponseEntity.ok(spettacoli);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Spettacolo> getSpettacoloById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(spettacoloService.getSpettacoloById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Spettacolo>> getAllSpettacoli() {
        return ResponseEntity.ok(spettacoloService.getAllSpettacoli());
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PostMapping("/create")
    public ResponseEntity<Spettacolo> createSpettacolo(@RequestBody SpettacoloRequest spettacoloRequest) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(spettacoloService.createSpettacolo(spettacoloRequest));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PutMapping("/update/{id}")
    public ResponseEntity<Spettacolo> updateSpettacolo(@PathVariable Long id, @RequestBody SpettacoloRequest spettacoloRequest) throws EntityNotFoundException {
        return ResponseEntity.ok(spettacoloService.updateSpettacolo(id, spettacoloRequest));
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @DeleteMapping("/delete/{id}")
    public void deleteSpettacoloById(@PathVariable Long id) throws EntityNotFoundException {
        spettacoloService.deleteSpettacoloById(id);
    }

    @Secured({"SUPERADMIN", "ADMIN"})
    @PutMapping("/upload_documento/{id}")
    public ResponseEntity<?> uploadDocumento(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws EntityNotFoundException {
        try {
            // Controllo il tipo di file
            if (!file.getContentType().equals("application/pdf")) {
                return new ResponseEntity<>(new GenericResponse("Solo file PDF sono ammessi"), HttpStatus.BAD_REQUEST);
            }
            spettacoloService.uploadDocumento(id, file);
            return new ResponseEntity<>(new GenericResponse("File caricato con successo"), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(new ErrorResponse("InputOutputException","Errore nel caricamento del file"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download_documento/{id}")
    public ResponseEntity<GenericResponse> downloadDocumento(@PathVariable Long id, HttpServletResponse response) throws IOException {
        String pathFile = spettacoloService.getPath(id);
        Path filePath = Path.of(pathFile);
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType); // setto l'header content-type
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"");
        response.setContentLength((int) Files.size(filePath));
        Files.copy(filePath, response.getOutputStream());
        return new ResponseEntity<>(new GenericResponse("file scaricato con successo"), HttpStatus.OK);
    }
}
