package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Crew;

import java.util.List;
import java.util.Optional;

public interface CrewRepository {
    List<Crew> findAll();
    Optional<Crew> findByName(String name);
}
