package be.kdg.programming3.onepiece.business.service.impl;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import be.kdg.programming3.onepiece.data.repository.CharacterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository repository;

    public CharacterServiceImpl(CharacterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Character> getAllCharacters() {
        return repository.findAll();
    }

    @Override
    public List<Character> getCharactersByPowertype(Powertype powertype) {
        return repository.findByPowertype(powertype);
    }

    @Override
    public void addCharacter(String name, int age, String appearance, Powertype powertype, double power) {
        Character character = new Character(name, age, appearance, powertype, power);
        repository.save(character);
        logger.debug("Added character {}", character);
    }
}
