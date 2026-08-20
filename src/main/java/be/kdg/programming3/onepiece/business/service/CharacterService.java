package be.kdg.programming3.onepiece.business.service;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    List<Character> getAllCharacters();
    Optional<Character> getCharacterById(int id);
    List<Character> getCharactersByPowertype(Powertype powertype);
    List<Crew> getAllCrews();
    Optional<Crew> getCrewByName(String name);
    void addCharacter(String name, int age, String appearance, Powertype powertype, double power, String crewName);
}
