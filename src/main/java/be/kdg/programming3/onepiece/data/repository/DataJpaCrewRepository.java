package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Crew;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("datajpa")
@Transactional(readOnly = true)
public class DataJpaCrewRepository implements CrewRepository {

    private final SpringDataCrewRepository delegate;

    public DataJpaCrewRepository(SpringDataCrewRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Crew> findAll() {
        return delegate.findAll();
    }

    @Override
    public Optional<Crew> findByName(String name) {
        return delegate.findById(name);
    }
}
