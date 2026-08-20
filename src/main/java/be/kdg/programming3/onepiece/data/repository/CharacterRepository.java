package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository {
    List<Character> findAll();
    Optional<Character> findById(int id);
    List<Character> findByPowertype(Powertype powertype);
    List<Character> findByCrew(Crew crew);
    List<Character> findByBattleId(int battleId);

    List<Character> findByNameContaining(String name);
    List<Character> findByMinPower(double minPower);
    List<Character> findByMinBattles(int minBattles);

    int save(Character character);
    void delete(int id);
    void updateSwordName(int id, String swordName);
}
