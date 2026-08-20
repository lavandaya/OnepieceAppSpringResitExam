package be.kdg.programming3.onepiece.business.domain;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private final int id;
    private final String name;
    private final int age;
    private final String appearance;
    private final Powertype powertype;
    private final double power;
    private Crew crew;
    private final List<Battle> battles = new ArrayList<>();

    public Character(int id, String name, int age, String appearance, Powertype powertype, double power) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.appearance = appearance;
        this.powertype = powertype;
        this.power = power;
    }

    public Character(String name, int age, String appearance, Powertype powertype, double power) {
        this(0, name, age, appearance, powertype, power);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getAppearance() { return appearance; }
    public Powertype getPowertype() { return powertype; }
    public double getPower() { return power; }

    public Crew getCrew() { return crew; }
    public void setCrew(Crew crew) { this.crew = crew; }

    public List<Battle> getBattles() { return battles; }

    public void addBattle(Battle battle) {
        if (battle != null && !battles.contains(battle)) {
            battles.add(battle);
            battle.addCharacter(this);
        }
    }

    @Override
    public String toString() {
        return "Character #" + id + ", " + name + ", powertype - " + powertype + ", power - " + power + " DON";
    }
}
