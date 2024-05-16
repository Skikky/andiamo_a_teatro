package com.example.andiamo_a_teatro.entities;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

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
    @Check(constraints = "voto >= 0 AND voto <= 5")
    private Integer voto;
    @Column(nullable = false)
    private Timestamp timestamp;
    @ManyToOne(optional = false)
    @JoinColumn(name = "spettacolo_id", nullable = false)
    private Spettacolo spettacolo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;
}
