package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    @Query(value = "SELECT * FROM utente u WHERE u.email = :email", nativeQuery = true)
    Utente findUtenteByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM utente u WHERE u.comune_id = :comune_id", nativeQuery = true)
    List<Utente> findAllByComuneId(@Param("comune_id") Long comuneId);
}