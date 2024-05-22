package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.CommentiNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommentiNewsRepository extends JpaRepository<CommentiNews, Long> {

    @Query(value = "SELECT MAX(insert_time) FROM commenti_news WHERE utente_id = :id_utente", nativeQuery = true)
    LocalDateTime getLastCommentoTime(@Param("id_utente") Long id_utente);


}
