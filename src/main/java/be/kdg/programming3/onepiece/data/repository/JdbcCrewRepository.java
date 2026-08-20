package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Crew;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcCrewRepository implements CrewRepository {

    private final JdbcClient jdbcClient;

    public JdbcCrewRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Crew> findAll() {
        return jdbcClient.sql("SELECT name, has_bounty, ship_name FROM crews ORDER BY name")
                .query(this::mapCrew)
                .list();
    }

    @Override
    public Optional<Crew> findByName(String name) {
        return jdbcClient.sql("SELECT name, has_bounty, ship_name FROM crews WHERE name = :name")
                .param("name", name)
                .query(this::mapCrew)
                .optional();
    }

    private Crew mapCrew(ResultSet rs, int rowNum) throws SQLException {
        return new Crew(
                rs.getString("name"),
                rs.getBoolean("has_bounty"),
                rs.getString("ship_name"));
    }
}
