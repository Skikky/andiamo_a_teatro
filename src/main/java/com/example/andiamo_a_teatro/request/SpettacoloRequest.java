package com.example.andiamo_a_teatro.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpettacoloRequest {
    private String nome;
    private LocalDateTime orario;
    private Integer durata;
    private Double prezzo;
    private Long id_sala;
    private Long id_genere;
}
