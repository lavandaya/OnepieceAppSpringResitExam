package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class InMemoryCharacterRepository implements CharacterRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryCharacterRepository.class);

    private final DataFactory factory;

    public InMemoryCharacterRepository(DataFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Character> findAll() {
        return factory.getAllCharacters();
    }

    @Override
    public Optional<Character> findById(int id) {
        logger.debug("Finding character by id={}", id);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public List<Character> findByPowertype(Powertype powertype) {
        logger.debug("Finding characters by powertype={}", powertype);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getPowertype() == powertype)
                .toList();
    }

    @Override
    public List<Character> findByCrew(Crew crew) {
        logger.debug("Finding characters by crew '{}'", crew.getName());
        return factory.getAllCharacters().stream()
                .filter(c -> c.getCrew() != null && c.getCrew().getName().equals(crew.getName()))
                .toList();
    }

    @Override
    public List<Character> findByBattleId(int battleId) {
        logger.debug("Finding characters by battleId={}", battleId);
        return factory.getAllBattles().stream()
                .filter(b -> b.getId() == battleId)
                .findFirst()
                .map(b -> List.copyOf(b.getCharacters()))
                .orElse(List.of());
    }

    @Override
    public int save(Character character) {
        logger.debug("Saving character {}", character);
        return factory.addCharacter(character).getId();
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting character id={}", id);
        factory.removeCharacter(id);
    }

    @Override
    public void updateSwordName(int id, String swordName) {
        logger.debug("Updating sword name of character {} to '{}'", id, swordName);
        factory.updateSwordName(id, swordName);
    }

    @Override
    public List<Character> findByNameContaining(String name) {
        logger.debug("Finding characters by name containing '{}'", name);
        String needle = name.toLowerCase();
        return factory.getAllCharacters().stream()
                .filter(c -> c.getName().toLowerCase().contains(needle))
                .toList();
    }

    @Override
    public List<Character> findByMinPower(double minPower) {
        logger.debug("Finding characters with power >= {}", minPower);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getPower() >= minPower)
                .toList();
    }

    @Override
    public List<Character> findByMinBattles(int minBattles) {
        logger.debug("Finding characters with at least {} battles", minBattles);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getBattles().size() >= minBattles)
                .toList();
    }
}
