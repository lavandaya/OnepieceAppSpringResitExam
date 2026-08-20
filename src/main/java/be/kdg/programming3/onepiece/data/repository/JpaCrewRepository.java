package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Crew;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@Transactional(readOnly = true)
public class JpaCrewRepository implements CrewRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Crew> findAll() {
        return em.createQuery("SELECT c FROM Crew c ORDER BY c.name", Crew.class)
                .getResultList();
    }

    @Override
    public Optional<Crew> findByName(String name) {
        return Optional.ofNullable(em.find(Crew.class, name));
    }
}
