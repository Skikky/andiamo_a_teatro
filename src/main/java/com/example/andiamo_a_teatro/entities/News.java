package com.example.andiamo_a_teatro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String body;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_likes",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id")
    )
    private Set<Utente> likedByUsers = new HashSet<>();
    @Column(nullable = false)
    private Integer likes = 0;

    @PrePersist
    @PreUpdate
    private void validateLikesConsistency() {
        if (likedByUsers != null && likedByUsers.size() != likes) {
            throw new IllegalStateException("il numero di utenti e il numero di likes deve essere lo stesso!");
        }
    }
}
