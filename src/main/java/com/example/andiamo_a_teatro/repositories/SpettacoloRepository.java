package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long> {
}
