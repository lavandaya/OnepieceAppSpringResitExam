package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.data.DataFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InMemoryBattleRepository implements BattleRepository {
    private final DataFactory factory;

    public InMemoryBattleRepository(DataFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Battle> findAll() {
        return factory.getAllBattles();
    }

    @Override
    public List<Battle> findByLocationAndDate(String location, LocalDate date) {
        String needle = (location == null) ? null : location.toLowerCase();
        return factory.getAllBattles().stream()
                .filter(b -> needle == null || needle.isBlank() || b.getLocation().toLowerCase().contains(needle))
                .filter(b -> date == null || b.getDate().toLocalDate().isEqual(date))
                .toList();
    }
}
