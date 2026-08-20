package be.kdg.programming3.onepiece.data;

import be.kdg.programming3.onepiece.business.domain.Battle;
import be.kdg.programming3.onepiece.business.domain.Character;
import be.kdg.programming3.onepiece.business.domain.Crew;
import be.kdg.programming3.onepiece.business.domain.Powertype;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataFactory {
    private final List<Character> characters = new ArrayList<>();
    private final List<Battle> battles = new ArrayList<>();
    private final List<Crew> crews = new ArrayList<>();

    @PostConstruct
    public void seed() {
        Character c1 = new Character(1, "Luffy", 18, "https://placehold.co/400x400/d62828/ffffff?text=Luffy", Powertype.DEVIL_FRUIT, 10);
        Character c2 = new Character(2, "Zoro", 20, "https://placehold.co/400x400/2a6f4e/ffffff?text=Zoro", Powertype.WILL, 9);
        Character c3 = new Character(3, "Sanji", 20, "https://placehold.co/400x400/e8a23d/000000?text=Sanji", Powertype.NO_POWER, 8);
        Character c4 = new Character(4, "Ussop", 19, "https://placehold.co/400x400/8a5a44/ffffff?text=Ussop", Powertype.NO_POWER, 1);
        Character c5 = new Character(5, "Nami", 19, "https://placehold.co/400x400/e07a9b/000000?text=Nami", Powertype.NO_POWER, 1);
        Character c6 = new Character(6, "Trafalgar", 21, "https://placehold.co/400x400/4a4e69/ffffff?text=Trafalgar", Powertype.DEVIL_FRUIT, 10);

        characters.add(c1); characters.add(c2); characters.add(c3);
        characters.add(c4); characters.add(c5); characters.add(c6);

        Battle b1 = new Battle(1, "Arlong Park showdown", "Arlong Park", LocalDateTime.of(2005, 7, 23, 12, 20), "Luffy");
        Battle b2 = new Battle(2, "Duel of Zoro and Mihawk", "Baratie", LocalDateTime.of(2005, 9, 11, 9, 3), "Zoro");
        Battle b3 = new Battle(3, "Candle show", "Island of Giants", LocalDateTime.of(2005, 12, 1, 18, 40), "Zoro");
        Battle b4 = new Battle(4, "Crocodile", "Alabasta", LocalDateTime.of(2006, 3, 15, 16, 45), "Luffy");
        Battle b5 = new Battle(5, "Enel", "Skypiea", LocalDateTime.of(2007, 8, 9, 10, 30), "Luffy");
        Battle b6 = new Battle(6, "Rob Lucci", "Enies Lobby", LocalDateTime.of(2008, 11, 2, 18, 15), "Ussop");
        Battle b7 = new Battle(7, "Gecko Moria", "Thriller Bark", LocalDateTime.of(2009, 5, 20, 22, 0), "Zoro");
        Battle b8 = new Battle(8, "Doflamingo", "Dressrosa", LocalDateTime.of(2013, 9, 28, 14, 10), "Nami");

        battles.add(b1); battles.add(b2); battles.add(b3); battles.add(b4);
        battles.add(b5); battles.add(b6); battles.add(b7); battles.add(b8);

        c1.addBattle(b1); c1.addBattle(b4); c1.addBattle(b5);
        c2.addBattle(b2); c2.addBattle(b3); c2.addBattle(b7);
        c3.addBattle(b1);
        c4.addBattle(b6); c4.addBattle(b1);
        c5.addBattle(b1); c5.addBattle(b8);
        c6.addBattle(b8);

        Crew crew1 = new Crew("Straw Hat Pirates", true, "Going Merry");
        Crew crew2 = new Crew("Heart Pirates", true, "Polar Tang");

        crew1.addCharacter(c1); crew1.addCharacter(c2); crew1.addCharacter(c3);
        crew1.addCharacter(c4); crew1.addCharacter(c5);
        crew2.addCharacter(c6);

        crews.add(crew1); crews.add(crew2);
    }

    public List<Character> getAllCharacters() { return new ArrayList<>(characters); }
    public List<Battle> getAllBattles() { return new ArrayList<>(battles); }
    public List<Crew> getAllCrews() { return new ArrayList<>(crews); }
}
