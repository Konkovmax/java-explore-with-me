package ru.practicum.statsserver.hit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface HitRepository extends JpaRepository<Hit, Integer> {

    Integer countDistinctByUriInAndTimeStampIsBetween(String[] uri, LocalDateTime start, LocalDateTime end);

    Integer countByUriInAndTimeStampIsBetween(String[] uri, LocalDateTime start, LocalDateTime end);

}
