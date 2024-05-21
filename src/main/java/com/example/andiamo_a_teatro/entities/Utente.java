package com.example.andiamo_a_teatro.entities;

import com.example.andiamo_a_teatro.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utente implements UserDetails {

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
    @Check(constraints = "saldo >= 0")
    private Double saldo;
    @Column(nullable = false)
    private String registrationToken;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comune_id")
    private Comune comune;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiUtente;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Recensione> recensioniUtente;
    @ManyToMany(mappedBy = "likedByUsers")
    private Set<News> likedNews = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
