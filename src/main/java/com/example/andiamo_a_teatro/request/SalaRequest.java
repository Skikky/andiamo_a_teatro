package com.example.andiamo_a_teatro.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaRequest {
    private Integer posti;
    private String nome;
    private Long id_sede;
}
