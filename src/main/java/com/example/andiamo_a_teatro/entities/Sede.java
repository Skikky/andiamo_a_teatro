package com.example.andiamo_a_teatro.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String indirizzo;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private Boolean isOpen;
    @ManyToOne
    @JoinColumn(name = "comune_id", nullable = false)
    private Comune comune;
}
