package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.CommentiNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentiNewsRepository extends JpaRepository<CommentiNews, Long> {
}
