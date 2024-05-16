package com.example.andiamo_a_teatro.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecensioneResponse {
    private String testo;
    private Integer voto;
    private Timestamp timestamp;
    private Long id_spettacolo;
    private Long id_utente;
}
