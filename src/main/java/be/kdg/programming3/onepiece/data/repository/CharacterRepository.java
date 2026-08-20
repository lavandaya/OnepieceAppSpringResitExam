package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository {
    List<Character> findAll();
    Optional<Character> findById(int id);
    List<Character> findByPowertype(Powertype powertype);
    int save(Character character);
}
