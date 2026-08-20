package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcCharacterRepository implements CharacterRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCharacterRepository.class);

    private static final String SELECT_BASE = """
            SELECT c.character_id, c.name, c.age, c.appearance, c.powertype, c.power,
                   cr.name AS crew_name, cr.has_bounty AS crew_has_bounty, cr.ship_name AS crew_ship_name
            FROM characters c
            LEFT JOIN crews cr ON cr.name = c.crew_name""";

    private final JdbcClient jdbcClient;

    public JdbcCharacterRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Character> findAll() {
        return jdbcClient.sql(SELECT_BASE + " ORDER BY c.character_id")
                .query(this::mapCharacter)
                .list();
    }

    @Override
    public Optional<Character> findById(int id) {
        logger.debug("Finding character by id={}", id);
        return jdbcClient.sql(SELECT_BASE + " WHERE c.character_id = :id")
                .param("id", id)
                .query(this::mapCharacter)
                .optional();
    }

    @Override
    public List<Character> findByPowertype(Powertype powertype) {
        logger.debug("Finding characters by powertype={}", powertype);
        return jdbcClient.sql(SELECT_BASE + " WHERE c.powertype = :powertype ORDER BY c.character_id")
                .param("powertype", powertype.name())
                .query(this::mapCharacter)
                .list();
    }

    @Override
    public List<Character> findByCrew(Crew crew) {
        logger.debug("Finding characters by crew '{}'", crew.getName());
        return jdbcClient.sql(SELECT_BASE + " WHERE c.crew_name = :crewName ORDER BY c.character_id")
                .param("crewName", crew.getName())
                .query(this::mapCharacter)
                .list();
    }

    @Override
    public List<Character> findByBattleId(int battleId) {
        logger.debug("Finding characters in battle id={}", battleId);
        return jdbcClient.sql(SELECT_BASE +
                        " JOIN character_battles cb ON cb.character_id = c.character_id" +
                        " WHERE cb.battle_id = :battleId ORDER BY c.character_id")
                .param("battleId", battleId)
                .query(this::mapCharacter)
                .list();
    }

    @Override
    public int save(Character character) {
        logger.debug("Saving character {}", character);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                        INSERT INTO characters (name, age, appearance, powertype, power, crew_name)
                        VALUES (:name, :age, :appearance, :powertype, :power, :crewName)
                        """)
                .param("name", character.getName())
                .param("age", character.getAge())
                .param("appearance", character.getAppearance())
                .param("powertype", character.getPowertype().name())
                .param("power", character.getPower())
                .param("crewName", character.getCrew() != null ? character.getCrew().getName() : null)
                .update(keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void delete(int id) {
        logger.debug("Deleting character id={}", id);
        jdbcClient.sql("DELETE FROM characters WHERE character_id = :id")
                .param("id", id)
                .update();
    }

    private Character mapCharacter(ResultSet rs, int rowNum) throws SQLException {
        Character character = new Character(
                rs.getInt("character_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("appearance"),
                Powertype.valueOf(rs.getString("powertype")),
                rs.getDouble("power"));

        String crewName = rs.getString("crew_name");
        if (crewName != null) {
            character.setCrew(new Crew(
                    crewName,
                    rs.getBoolean("crew_has_bounty"),
                    rs.getString("crew_ship_name")));
        }
        return character;
    }
}
