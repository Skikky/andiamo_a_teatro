package com.example.andiamo_a_teatro.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtenteResponse {
    private Long id_utente;
    private String nome;
    private String cognome;
    private String email;
    private String indirizzo;
    private String telefono;
    private LocalDate nascita;
    private String password;
    private Double saldo;
    private Long id_comune;
    private List<Long> id_biglietto;
    private List<Long> id_recensione;
}
