package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.domain.Swordsman;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@Transactional
public class JpaCharacterRepository implements CharacterRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaCharacterRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Character> findAll() {
        return em.createQuery("SELECT c FROM Character c ORDER BY c.id", Character.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findById(int id) {
        logger.debug("Finding character by id={}", id);
        return Optional.ofNullable(em.find(Character.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByPowertype(Powertype powertype) {
        logger.debug("Finding characters by powertype={}", powertype);
        return em.createQuery(
                        "SELECT c FROM Character c WHERE c.powertype = :powertype ORDER BY c.id",
                        Character.class)
                .setParameter("powertype", powertype)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByCrew(Crew crew) {
        logger.debug("Finding characters by crew '{}'", crew.getName());
        return em.createQuery(
                        "SELECT c FROM Character c WHERE c.crew.name = :crewName ORDER BY c.id",
                        Character.class)
                .setParameter("crewName", crew.getName())
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByBattleId(int battleId) {
        logger.debug("Finding characters in battle id={}", battleId);
        return em.createQuery(
                        "SELECT c FROM Character c JOIN c.battles b WHERE b.id = :battleId ORDER BY c.id",
                        Character.class)
                .setParameter("battleId", battleId)
                .getResultList();
    }

    @Override
    public int save(Character character) {
        logger.debug("Saving character {}", character);
        em.persist(character);
        return character.getId();
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting character id={}", id);
        Character character = em.find(Character.class, id);
        if (character != null) {
            em.remove(character);
        }
    }

    @Override
    public void updateSwordName(int id, String swordName) {
        logger.debug("Updating sword name of character {} to '{}'", id, swordName);
        Character character = em.find(Character.class, id);
        if (character instanceof Swordsman swordsman) {
            swordsman.setSwordName(swordName);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByNameContaining(String name) {
        logger.debug("Finding characters by name containing '{}'", name);
        return em.createQuery(
                        "SELECT c FROM Character c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY c.id",
                        Character.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByMinPower(double minPower) {
        logger.debug("Finding characters with power >= {}", minPower);
        return em.createQuery(
                        "SELECT c FROM Character c WHERE c.power >= :minPower ORDER BY c.id",
                        Character.class)
                .setParameter("minPower", minPower)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByMinBattles(int minBattles) {
        logger.debug("Finding characters with at least {} battles", minBattles);
        return em.createQuery(
                        "SELECT DISTINCT c FROM Character c WHERE SIZE(c.battles) >= :minBattles ORDER BY c.id",
                        Character.class)
                .setParameter("minBattles", minBattles)
                .getResultList();
    }
}
