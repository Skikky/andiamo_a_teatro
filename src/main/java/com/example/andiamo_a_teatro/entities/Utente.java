package com.example.andiamo_a_teatro.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String indirizzo;
    @Column(nullable = false, unique = true)
    private String telefono;
    @Column(nullable = false)
    @Check(constraints = "DATEDIFF(CURRENT_DATE, nascita) / 365 > 4")
    private LocalDate nascita;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean isLoggato;
}
