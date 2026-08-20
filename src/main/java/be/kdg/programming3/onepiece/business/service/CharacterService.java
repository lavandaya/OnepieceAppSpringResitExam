package be.kdg.programming3.onepiece.business.service;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;

import java.util.List;

public interface CharacterService {
    List<Character> getAllCharacters();
    List<Character> getCharactersByPowertype(Powertype powertype);
}
