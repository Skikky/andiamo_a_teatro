package com.example.andiamo_a_teatro.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SedeRequest {
    private String indirizzo;
    private String nome;
    private Boolean isOpen;
    private Long id_comune;
}
