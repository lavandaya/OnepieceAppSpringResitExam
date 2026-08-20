package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.domain.Character;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
@Profile("datajpa")
@Transactional
public class DataJpaBattleRepository implements BattleRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataJpaBattleRepository.class);

    private final SpringDataBattleRepository delegate;

    @PersistenceContext
    private EntityManager em;

    public DataJpaBattleRepository(SpringDataBattleRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findAll() {
        return delegate.findAllByOrderById();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Battle> findById(int id) {
        logger.debug("Finding battle by id={}", id);
        return delegate.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findByLocationAndDate(String location, LocalDate date) {
        logger.debug("Finding battles: location='{}', date={}", location, date);
        String needle = (location == null || location.isBlank()) ? null : location;
        LocalDateTime start = (date == null) ? null : date.atStartOfDay();
        LocalDateTime end = (date == null) ? null : date.plusDays(1).atStartOfDay();

        if (needle != null && start != null) {
            return delegate.findByLocationContainingIgnoreCaseAndDateBetweenOrderById(needle, start, end);
        } else if (needle != null) {
            return delegate.findByLocationContainingIgnoreCaseOrderById(needle);
        } else if (start != null) {
            return delegate.findByDateBetweenOrderById(start, end);
        } else {
            return delegate.findAllByOrderById();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Battle> findByCharacterId(int characterId) {
        logger.debug("Finding battles fought by character id={}", characterId);
        return delegate.findByCharacters_IdOrderById(characterId);
    }

    @Override
    public int save(Battle battle) {
        logger.debug("Saving battle {}", battle);
        return delegate.save(battle).getId();
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
        delegate.findById(id).ifPresent(battle -> {
            // Battle is the inverse side of the many-to-many; clear links from the owning side
            // first so Hibernate removes rows from character_battles before deleting the battle row.
            for (Character c : new ArrayList<>(battle.getCharacters())) {
                c.getBattles().remove(battle);
            }
            delegate.delete(battle);
        });
    }
}
