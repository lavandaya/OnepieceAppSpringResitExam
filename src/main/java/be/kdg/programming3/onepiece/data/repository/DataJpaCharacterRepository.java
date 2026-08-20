package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.domain.Swordsman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("datajpa")
@Transactional
public class DataJpaCharacterRepository implements CharacterRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataJpaCharacterRepository.class);

    private final SpringDataCharacterRepository delegate;

    public DataJpaCharacterRepository(SpringDataCharacterRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findAll() {
        return delegate.findAllByOrderById();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findById(int id) {
        logger.debug("Finding character by id={}", id);
        return delegate.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByPowertype(Powertype powertype) {
        logger.debug("Finding characters by powertype={}", powertype);
        return delegate.findByPowertypeOrderById(powertype);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByCrew(Crew crew) {
        logger.debug("Finding characters by crew '{}'", crew.getName());
        return delegate.findByCrew_NameOrderById(crew.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByBattleId(int battleId) {
        logger.debug("Finding characters in battle id={}", battleId);
        return delegate.findByBattles_IdOrderById(battleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByNameContaining(String name) {
        logger.debug("Finding characters by name containing '{}'", name);
        return delegate.findByNameContainingIgnoreCaseOrderById(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByMinPower(double minPower) {
        logger.debug("Finding characters with power >= {}", minPower);
        return delegate.findByPowerGreaterThanEqualOrderById(minPower);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findByMinBattles(int minBattles) {
        logger.debug("Finding characters with at least {} battles", minBattles);
        return delegate.findByMinBattles(minBattles);
    }

    @Override
    public int save(Character character) {
        logger.debug("Saving character {}", character);
        return delegate.save(character).getId();
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting character id={}", id);
        delegate.deleteById(id);
    }

    @Override
    public void updateSwordName(int id, String swordName) {
        logger.debug("Updating sword name of character {} to '{}'", id, swordName);
        delegate.findById(id)
                .filter(Swordsman.class::isInstance)
                .map(Swordsman.class::cast)
                .ifPresent(s -> s.setSwordName(swordName));
    }
}
