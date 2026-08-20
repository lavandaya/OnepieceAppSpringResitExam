package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Battle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcBattleRepository implements BattleRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcBattleRepository.class);
    private static final String SELECT_BASE =
            "SELECT battle_id, name, location, fought_at, winner FROM battles";

    private final JdbcClient jdbcClient;

    public JdbcBattleRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Battle> findAll() {
        return jdbcClient.sql(SELECT_BASE + " ORDER BY battle_id")
                .query(this::mapBattle)
                .list();
    }

    @Override
    public Optional<Battle> findById(int id) {
        logger.debug("Finding battle by id={}", id);
        return jdbcClient.sql(SELECT_BASE + " WHERE battle_id = :id")
                .param("id", id)
                .query(this::mapBattle)
                .optional();
    }

    @Override
    public List<Battle> findByLocationAndDate(String location, LocalDate date) {
        logger.debug("Finding battles: location='{}', date={}", location, date);
        String needle = (location == null || location.isBlank()) ? null : location.toLowerCase();

        return jdbcClient.sql("""
                        SELECT battle_id, name, location, fought_at, winner
                        FROM battles
                        WHERE (:needle IS NULL OR LOWER(location) LIKE CONCAT('%', :needle, '%'))
                          AND (:date IS NULL OR CAST(fought_at AS DATE) = :date)
                        ORDER BY battle_id
                        """)
                .param("needle", needle)
                .param("date", date)
                .query(this::mapBattle)
                .list();
    }

    @Override
    public List<Battle> findByCharacterId(int characterId) {
        logger.debug("Finding battles fought by character id={}", characterId);
        return jdbcClient.sql("""
                        SELECT b.battle_id, b.name, b.location, b.fought_at, b.winner
                        FROM battles b
                        JOIN character_battles cb ON cb.battle_id = b.battle_id
                        WHERE cb.character_id = :characterId
                        ORDER BY b.battle_id
                        """)
                .param("characterId", characterId)
                .query(this::mapBattle)
                .list();
    }

    @Override
    public int save(Battle battle) {
        logger.debug("Saving battle {}", battle);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                        INSERT INTO battles (name, location, fought_at, winner)
                        VALUES (:name, :location, :foughtAt, :winner)
                        """)
                .param("name", battle.getName())
                .param("location", battle.getLocation())
                .param("foughtAt", battle.getDate())
                .param("winner", battle.getWinner())
                .update(keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addCharacterToBattle(int battleId, int characterId) {
        logger.debug("Linking character {} to battle {}", characterId, battleId);
        jdbcClient.sql("""
                        INSERT INTO character_battles (character_id, battle_id)
                        VALUES (:characterId, :battleId)
                        """)
                .param("characterId", characterId)
                .param("battleId", battleId)
                .update();
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting battle id={}", id);
        jdbcClient.sql("DELETE FROM battles WHERE battle_id = :id")
                .param("id", id)
                .update();
    }

    private Battle mapBattle(ResultSet rs, int rowNum) throws SQLException {
        return new Battle(
                rs.getInt("battle_id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getTimestamp("fought_at").toLocalDateTime(),
                rs.getString("winner"));
    }
}
