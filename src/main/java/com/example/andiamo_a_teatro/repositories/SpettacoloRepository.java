package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long>, JpaSpecificationExecutor<Spettacolo> {
    @Query(value = "SELECT documento FROM spettacolo WHERE id =:id_spettacolo", nativeQuery = true)
    String getFilePath(@Param("id_spettacolo") Long id_spettacolo);
}
/*
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long> {
     @Query(value = "SELECT s FROM Spettacolo s " +
            "JOIN s.sala sala " +
            "JOIN sala.sede sede " +
            "JOIN sede.comune comune " +
            "WHERE (:idGenere IS NULL OR s.genere_id = :idGenere) " +
            "AND (:idComune IS NULL OR comune.id = :idComune) " +
            "AND (:isOpen IS NULL OR sede.is_open = :isOpen) " +
            "AND (:dataInizio IS NULL OR s.orario >= :dataInizio) " +
            "AND (:dataFine IS NULL OR s.orario <= :dataFine)")
    List<Spettacolo> findSpettacoliByFilters(@Param("idGenere") Long idGenere,
                                             @Param("idComune") Long idComune,
                                             @Param("isOpen") Boolean isOpen,
                                             @Param("dataInizio") LocalDateTime dataInizio,
                                             @Param("dataFine") LocalDateTime dataFine);
}
     */
