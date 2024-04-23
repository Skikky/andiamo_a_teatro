package com.example.andiamo_a_teatro.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Check(constraints = "fila > 0")
    private Integer fila;
    @Column(nullable = false)
    @Check(constraints = "numero > 0")
    private Integer numero;
    @OneToOne
    private Sala sala;
}
