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
public class  Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente = null;
    @ManyToOne(optional = false)
    @JoinColumn(name = "spettacolo_id", nullable = false)
    private Spettacolo spettacolo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "posto_id", nullable = false)
    private Posto posto;
}
