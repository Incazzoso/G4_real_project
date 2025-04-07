package g4.group;

public class Card {

    //variabili
    private int health;             //la vita della carta HP
    private int damage;             //il danno che fa
    private int cost;               //il costo per schierarla
    private String name;            //il nome della carta
    private String description;     //una descrizione (opzionale)


    //costruttore (completo)
    Card(String name, int health, int damage, int cost, String description){
        this.health = health;
        this.damage = damage;
        this.cost = cost;
        this.name = name;
        this.description = description;
    }

    //costruttore (senza descrizione)
    Card(String name, int health, int damage, int cost){
        this.health = health;
        this.damage = damage;
        this.cost = cost;
        this.name = name;
    }

    //getter
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    //setter
    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
