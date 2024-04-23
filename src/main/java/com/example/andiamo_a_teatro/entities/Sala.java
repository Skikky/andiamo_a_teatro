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
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Check(constraints = "posti > 0")
    private Integer posti;
    @Column(nullable = false)
    private String nome;
    @OneToOne
    private Sede sede;
}
