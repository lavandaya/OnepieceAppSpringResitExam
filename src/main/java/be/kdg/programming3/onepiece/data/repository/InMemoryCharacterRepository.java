package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryCharacterRepository implements CharacterRepository {
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
        return factory.getAllCharacters().stream()
                .filter(c -> c.getPowertype() == powertype)
                .toList();
    }
}
