package com.example.andiamo_a_teatro.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BigliettoResponse {

    private Long id_biglietto;
    private Timestamp timestamp;
    private Long id_utente;
    private Long id_spettacolo;
    private Long id_posto;
}
