package g4.group;

public class Unit extends Card{

    Unit(String name, int health, int damage, int cost){
        super(name, health, damage, cost);
    }
    Unit(String name, int health, int damage, int cost, String description) {
        super(name, health, damage, cost, description);
    }
}
