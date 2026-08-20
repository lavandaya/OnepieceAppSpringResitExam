package be.kdg.programming3.onepiece.business.service;

import be.kdg.programming3.onepiece.business.domain.Battle;

import java.time.LocalDate;
import java.util.List;

public interface BattleService {
    List<Battle> getAllBattles();
    List<Battle> findBattles(String location, LocalDate date);
}
