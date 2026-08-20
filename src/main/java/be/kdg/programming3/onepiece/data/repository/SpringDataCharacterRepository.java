package be.kdg.programming3.onepiece.data.repository;

import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("datajpa")
public interface SpringDataCharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findAllByOrderById();
    List<Character> findByPowertypeOrderById(Powertype powertype);
    List<Character> findByCrew_NameOrderById(String crewName);
    List<Character> findByBattles_IdOrderById(int battleId);

    List<Character> findByNameContainingIgnoreCaseOrderById(String name);
    List<Character> findByPowerGreaterThanEqualOrderById(double minPower);

    @Query("SELECT DISTINCT c FROM Character c WHERE SIZE(c.battles) >= :minBattles ORDER BY c.id")
    List<Character> findByMinBattles(@Param("minBattles") int minBattles);
}
