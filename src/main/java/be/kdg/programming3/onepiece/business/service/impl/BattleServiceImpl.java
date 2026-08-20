package be.kdg.programming3.onepiece.business.service.impl;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.service.BattleService;
import be.kdg.programming3.onepiece.data.repository.BattleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BattleServiceImpl implements BattleService {
    private static final Logger logger = LoggerFactory.getLogger(BattleServiceImpl.class);

    private final BattleRepository repository;

    public BattleServiceImpl(BattleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Battle> getAllBattles() {
        return repository.findAll();
    }

    @Override
    public Optional<Battle> getBattleById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Battle> findBattles(String location, LocalDate date) {
        return repository.findByLocationAndDate(location, date);
    }

    @Override
    public List<Battle> getBattlesForCharacter(int characterId) {
        return repository.findByCharacterId(characterId);
    }

    @Override
    public void addBattle(String name, String location, LocalDateTime date, String winner, List<Integer> characterIds) {
        Battle battle = new Battle(name, location, date, winner);
        int battleId = repository.save(battle);

        if (characterIds != null) {
            characterIds.forEach(charId -> repository.addCharacterToBattle(battleId, charId));
        }

        logger.debug("Added battle {} with {} character(s)", battle,
                characterIds == null ? 0 : characterIds.size());
    }

    @Override
    public void deleteBattle(int id) {
        logger.debug("Deleting battle id={}", id);
        repository.delete(id);
    }
}
