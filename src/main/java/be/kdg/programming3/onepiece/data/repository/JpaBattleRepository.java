package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.domain.Character;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@Transactional
public class JpaBattleRepository implements BattleRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaBattleRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findAll() {
        return em.createQuery("SELECT b FROM Battle b ORDER BY b.id", Battle.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Battle> findById(int id) {
        logger.debug("Finding battle by id={}", id);
        return Optional.ofNullable(em.find(Battle.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findByLocationAndDate(String location, LocalDate date) {
        logger.debug("Finding battles: location='{}', date={}", location, date);
        String needle = (location == null || location.isBlank()) ? null : location.toLowerCase();
        LocalDateTime start = (date == null) ? null : date.atStartOfDay();
        LocalDateTime end = (date == null) ? null : date.plusDays(1).atStartOfDay();

        // Build the JPQL dynamically so unused filters never bind a null parameter:
        // PostgreSQL's JDBC driver cannot infer a type for an untyped null bind value.
        StringBuilder jpql = new StringBuilder("SELECT b FROM Battle b WHERE 1=1");
        if (needle != null) {
            jpql.append(" AND LOWER(b.location) LIKE CONCAT('%', :needle, '%')");
        }
        if (start != null) {
            jpql.append(" AND (b.date >= :start AND b.date < :end)");
        }
        jpql.append(" ORDER BY b.id");

        TypedQuery<Battle> query = em.createQuery(jpql.toString(), Battle.class);
        if (needle != null) query.setParameter("needle", needle);
        if (start != null) {
            query.setParameter("start", start);
            query.setParameter("end", end);
        }
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findByCharacterId(int characterId) {
        logger.debug("Finding battles fought by character id={}", characterId);
        return em.createQuery(
                        "SELECT b FROM Battle b JOIN b.characters c WHERE c.id = :characterId ORDER BY b.id",
                        Battle.class)
                .setParameter("characterId", characterId)
                .getResultList();
    }

    @Override
    public int save(Battle battle) {
        logger.debug("Saving battle {}", battle);
        em.persist(battle);
        return battle.getId();
    }

    @Override
    public void addCharacterToBattle(int battleId, int characterId) {
        logger.debug("Linking character {} to battle {}", characterId, battleId);
        Battle battle = em.find(Battle.class, battleId);
        Character character = em.find(Character.class, characterId);
        if (battle != null && character != null) {
            character.addBattle(battle);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting battle id={}", id);
        Battle battle = em.find(Battle.class, id);
        if (battle == null) return;

        // Battle is the inverse side of the many-to-many; clear links from the owning side first
        // so Hibernate removes rows from character_battles before deleting the battle row.
        for (Character c : new ArrayList<>(battle.getCharacters())) {
            c.getBattles().remove(battle);
        }
        em.remove(battle);
    }
}
