package com.example.andiamo_a_teatro.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    private String nome;
    private String cognome;
    private LocalDate nascita;
    private String indirizzo;
    private String telefono;
    private String email;
    private String password;
    private Double saldo;
    private Long id_comune;
}
