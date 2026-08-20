package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
    public List<Character> findByPowertype(Powertype powertype) {
        logger.debug("Finding characters by powertype={}", powertype);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getPowertype() == powertype)
                .toList();
    }

    @Override
    public void save(Character character) {
        logger.debug("Saving character {}", character);
        factory.addCharacter(character);
    }
}
