package com.example.andiamo_a_teatro.request;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BigliettoRequest {
    private Long id_biglietto;
    private Timestamp timestamp;
    private Long id_spettacolo;
    private Long id_posto;
}
