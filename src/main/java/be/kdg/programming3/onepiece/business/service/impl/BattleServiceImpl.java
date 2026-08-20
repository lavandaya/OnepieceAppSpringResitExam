package be.kdg.programming3.onepiece.business.service.impl;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.service.BattleService;
import be.kdg.programming3.onepiece.data.repository.BattleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BattleServiceImpl implements BattleService {
    private final BattleRepository repository;

    public BattleServiceImpl(BattleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Battle> getAllBattles() {
        return repository.findAll();
    }

    @Override
    public List<Battle> findBattles(String location, LocalDate date) {
        return repository.findByLocationAndDate(location, date);
    }
}
