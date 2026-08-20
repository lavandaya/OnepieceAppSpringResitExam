package be.kdg.programming3.onepiece.business.domain;

import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String name;
    private final boolean hasBounty;
    private final String shipName;
    private final List<Character> members = new ArrayList<>();

    public Crew(String name, boolean hasBounty, String shipName) {
        this.name = name;
        this.hasBounty = hasBounty;
        this.shipName = shipName;
    }

    public String getName() { return name; }
    public boolean isHasBounty() { return hasBounty; }
    public String getShipName() { return shipName; }

    public List<Character> getMembers() { return members; }

    public void addCharacter(Character character) {
        if (character != null && !members.contains(character)) {
            members.add(character);
            character.setCrew(this);
        }
    }

    @Override
    public String toString() {
        return "Crew{" + name + ", ship='" + shipName + "'}";
    }
}
