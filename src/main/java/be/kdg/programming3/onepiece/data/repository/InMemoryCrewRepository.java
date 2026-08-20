package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class InMemoryCrewRepository implements CrewRepository {
    private final DataFactory factory;

    public InMemoryCrewRepository(DataFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Crew> findAll() {
        return factory.getAllCrews();
    }

    @Override
    public Optional<Crew> findByName(String name) {
        return factory.getAllCrews().stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst();
    }
}
