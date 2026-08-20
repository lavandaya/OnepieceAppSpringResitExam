package be.kdg.programming3.onepiece.business.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Battle {
    private final int id;
    private final String name;
    private final String location;
    private final LocalDateTime date;
    private final String winner;
    private final List<Character> characters = new ArrayList<>();

    public Battle(int id, String name, String location, LocalDateTime date, String winner) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.winner = winner;
    }

    public Battle(String name, String location, LocalDateTime date, String winner) {
        this(0, name, location, date, winner);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public LocalDateTime getDate() { return date; }
    public String getWinner() { return winner; }

    public List<Character> getCharacters() { return characters; }

    public void addCharacter(Character character) {
        if (character != null && !characters.contains(character)) {
            characters.add(character);
            character.addBattle(this);
        }
    }

    @Override
    public String toString() {
        return "Battle #" + id + " {" + name + ", " + location + ", " + date + ", winner=" + winner + "}";
    }
}
