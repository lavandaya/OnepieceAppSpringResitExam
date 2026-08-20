package be.kdg.programming3.onepiece.data;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Profile("jpa")
@Order(1)
public class JpaDataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(JpaDataSeeder.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void run(String... args) {
        long crewCount = em.createQuery("SELECT COUNT(c) FROM Crew c", Long.class).getSingleResult();
        if (crewCount > 0) {
            logger.debug("Database already seeded, skipping");
            return;
        }
        logger.debug("Seeding JPA database");

        Crew straw = new Crew("Straw Hat Pirates", true, "Going Merry");
        Crew heart = new Crew("Heart Pirates", true, "Polar Tang");
        em.persist(straw);
        em.persist(heart);

        Character luffy = newMember("Luffy", 18, "https://placehold.co/400x400/d62828/ffffff?text=Luffy", Powertype.DEVIL_FRUIT, 10, straw);
        Character zoro = newMember("Zoro", 20, "https://placehold.co/400x400/2a6f4e/ffffff?text=Zoro", Powertype.WILL, 9, straw);
        Character sanji = newMember("Sanji", 20, "https://placehold.co/400x400/e8a23d/000000?text=Sanji", Powertype.NO_POWER, 8, straw);
        Character ussop = newMember("Ussop", 19, "https://placehold.co/400x400/8a5a44/ffffff?text=Ussop", Powertype.NO_POWER, 1, straw);
        Character nami = newMember("Nami", 19, "https://placehold.co/400x400/e07a9b/000000?text=Nami", Powertype.NO_POWER, 1, straw);
        Character traf = newMember("Trafalgar", 21, "https://placehold.co/400x400/4a4e69/ffffff?text=Trafalgar", Powertype.DEVIL_FRUIT, 10, heart);

        Battle b1 = newBattle("Arlong Park showdown", "Arlong Park", LocalDateTime.of(2005, 7, 23, 12, 20), "Luffy");
        Battle b2 = newBattle("Duel of Zoro and Mihawk", "Baratie", LocalDateTime.of(2005, 9, 11, 9, 3), "Zoro");
        Battle b3 = newBattle("Candle show", "Island of Giants", LocalDateTime.of(2005, 12, 1, 18, 40), "Zoro");
        Battle b4 = newBattle("Crocodile", "Alabasta", LocalDateTime.of(2006, 3, 15, 16, 45), "Luffy");
        Battle b5 = newBattle("Enel", "Skypiea", LocalDateTime.of(2007, 8, 9, 10, 30), "Luffy");
        Battle b6 = newBattle("Rob Lucci", "Enies Lobby", LocalDateTime.of(2008, 11, 2, 18, 15), "Ussop");
        Battle b7 = newBattle("Gecko Moria", "Thriller Bark", LocalDateTime.of(2009, 5, 20, 22, 0), "Zoro");
        Battle b8 = newBattle("Doflamingo", "Dressrosa", LocalDateTime.of(2013, 9, 28, 14, 10), "Nami");

        luffy.addBattle(b1); luffy.addBattle(b4); luffy.addBattle(b5);
        zoro.addBattle(b2); zoro.addBattle(b3); zoro.addBattle(b7);
        sanji.addBattle(b1);
        ussop.addBattle(b6); ussop.addBattle(b1);
        nami.addBattle(b1); nami.addBattle(b8);
        traf.addBattle(b8);

        logger.debug("Seeded JPA database successfully");
    }

    private Character newMember(String name, int age, String appearance, Powertype pt, double power, Crew crew) {
        Character c = new Character(name, age, appearance, pt, power);
        c.setCrew(crew);
        em.persist(c);
        return c;
    }

    private Battle newBattle(String name, String location, LocalDateTime date, String winner) {
        Battle b = new Battle(name, location, date, winner);
        em.persist(b);
        return b;
    }
}
