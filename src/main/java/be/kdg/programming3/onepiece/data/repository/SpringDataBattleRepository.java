package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Profile("datajpa")
public interface SpringDataBattleRepository extends JpaRepository<Battle, Integer> {
    List<Battle> findAllByOrderById();
    List<Battle> findByCharacters_IdOrderById(int characterId);

    // Split into distinct derived-query methods per filter combination, rather than one
    // query with "OR :param IS NULL" clauses: PostgreSQL's JDBC driver cannot infer a type
    // for an untyped null bind value, so an unused filter must never bind null at all.
    List<Battle> findByLocationContainingIgnoreCaseOrderById(String location);
    List<Battle> findByDateBetweenOrderById(LocalDateTime start, LocalDateTime end);
    List<Battle> findByLocationContainingIgnoreCaseAndDateBetweenOrderById(String location, LocalDateTime start, LocalDateTime end);
}
