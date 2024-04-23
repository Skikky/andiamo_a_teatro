package com.example.andiamo_a_teatro.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Timestamp timestamp;
    @OneToOne
    private Utente utente;
    @OneToOne
    private Spettacolo spettacolo;
    @OneToOne
    private Posto posto;

}
