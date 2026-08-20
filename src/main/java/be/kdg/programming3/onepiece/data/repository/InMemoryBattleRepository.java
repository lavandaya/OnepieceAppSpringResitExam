package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class InMemoryBattleRepository implements BattleRepository {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryBattleRepository.class);

    private final DataFactory factory;

    public InMemoryBattleRepository(DataFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Battle> findAll() {
        return factory.getAllBattles();
    }

    @Override
    public Optional<Battle> findById(int id) {
        logger.debug("Finding battle by id={}", id);
        return factory.getAllBattles().stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }

    @Override
    public List<Battle> findByLocationAndDate(String location, LocalDate date) {
        logger.debug("Finding battles: location='{}', date={}", location, date);
        String needle = (location == null) ? null : location.toLowerCase();
        return factory.getAllBattles().stream()
                .filter(b -> needle == null || needle.isBlank() || b.getLocation().toLowerCase().contains(needle))
                .filter(b -> date == null || b.getDate().toLocalDate().isEqual(date))
                .toList();
    }

    @Override
    public List<Battle> findByCharacterId(int characterId) {
        logger.debug("Finding battles by characterId={}", characterId);
        return factory.getAllCharacters().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst()
                .map(c -> List.copyOf(c.getBattles()))
                .orElse(List.of());
    }

    @Override
    public int save(Battle battle) {
        logger.debug("Saving battle {}", battle);
        return factory.addBattle(battle).getId();
    }

    @Override
    public void addCharacterToBattle(int battleId, int characterId) {
        logger.debug("Linking character {} to battle {}", characterId, battleId);
        Battle battle = factory.getAllBattles().stream()
                .filter(b -> b.getId() == battleId)
                .findFirst().orElse(null);
        Character character = factory.getAllCharacters().stream()
                .filter(c -> c.getId() == characterId)
                .findFirst().orElse(null);
        if (battle != null && character != null) {
            battle.addCharacter(character);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting battle id={}", id);
        factory.removeBattle(id);
    }
}
