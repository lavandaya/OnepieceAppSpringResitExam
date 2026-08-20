package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;

import java.util.List;

public interface CharacterRepository {
    List<Character> findAll();
    List<Character> findByPowertype(Powertype powertype);
    void save(Character character);
}
