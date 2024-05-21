package com.example.andiamo_a_teatro.repositories;

import com.example.andiamo_a_teatro.entities.ScheduledNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledNewsRepository extends JpaRepository<ScheduledNews, Long> {
}
