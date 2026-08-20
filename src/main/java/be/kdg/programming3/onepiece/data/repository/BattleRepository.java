package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BattleRepository {
    List<Battle> findAll();
    Optional<Battle> findById(int id);
    List<Battle> findByLocationAndDate(String location, LocalDate date);
    List<Battle> findByCharacterId(int characterId);
    int save(Battle battle);
    void addCharacterToBattle(int battleId, int characterId);
    void delete(int id);
}
