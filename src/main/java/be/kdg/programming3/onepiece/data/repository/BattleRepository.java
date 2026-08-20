package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;

import java.time.LocalDate;
import java.util.List;

public interface BattleRepository {
    List<Battle> findAll();
    List<Battle> findByLocationAndDate(String location, LocalDate date);
    void save(Battle battle);
    void addCharacterToBattle(int battleId, int characterId);
}
