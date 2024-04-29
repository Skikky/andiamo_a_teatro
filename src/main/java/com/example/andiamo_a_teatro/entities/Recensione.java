package com.example.andiamo_a_teatro.entities;

import lombok.*;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String testo;
    @Column(nullable = false)
    private int voto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spettacolo_id", nullable = false)
    private Spettacolo spettacolo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
}
