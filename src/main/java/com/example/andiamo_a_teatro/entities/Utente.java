package com.example.andiamo_a_teatro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false)
    @Check(constraints = "saldo > 0")
    private Double saldo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiUtente;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Recensione> recensioniUtente;
}
