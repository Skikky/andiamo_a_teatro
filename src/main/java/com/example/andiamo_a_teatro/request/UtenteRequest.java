package com.example.andiamo_a_teatro.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenteRequest {
    private String nome;
    private String cognome;
    private String email;
    private String indirizzo;
    private String telefono;
    private LocalDate nascita;
    private String password;
    private Double saldo;
    private List<Long> id_biglietto;
}
