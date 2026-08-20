package be.kdg.programming3.onepiece.business.service.impl;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import be.kdg.programming3.onepiece.business.service.CharacterService;
import be.kdg.programming3.onepiece.data.repository.CharacterRepository;
import be.kdg.programming3.onepiece.data.repository.CrewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private static final Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository repository;
    private final CrewRepository crewRepository;

    public CharacterServiceImpl(CharacterRepository repository, CrewRepository crewRepository) {
        this.repository = repository;
        this.crewRepository = crewRepository;
    }

    @Override
    public List<Character> getAllCharacters() {
        return repository.findAll();
    }

    @Override
    public Optional<Character> getCharacterById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Character> getCharactersByPowertype(Powertype powertype) {
        return repository.findByPowertype(powertype);
    }

    @Override
    public List<Crew> getAllCrews() {
        return crewRepository.findAll();
    }

    @Override
    public Optional<Crew> getCrewByName(String name) {
        return crewRepository.findByName(name);
    }

    @Override
    public void addCharacter(String name, int age, String appearance,
                              Powertype powertype, double power, String crewName) {
        Character character = new Character(name, age, appearance, powertype, power);
        crewRepository.findByName(crewName).ifPresent(character::setCrew);

        repository.save(character);
        logger.debug("Added character {} (crew='{}')", character, crewName);
    }
}
