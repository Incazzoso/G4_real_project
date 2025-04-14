package g4.group.allCardUtilities;

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
        this.damage = Math.abs(damage);
        this.cost = Math.abs(cost);

        if(name == null){
            this.name = "";
        }else{
            this.name = name;
        }

        if(description == null){
            this.description = "";
        }else{
            this.description = description;
        }
    }

    //costruttore (senza descrizione)
    Card(String name, int health, int damage, int cost){
        this.health = health;
        this.damage = Math.abs(damage);
        this.cost = Math.abs(cost);
        if(name == null){
            this.name = "";
        }else{
            this.name = name;
        }
            this.description = "";
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
        this.damage = Math.abs(damage);
    }

    public void setCost(int cost) {
        this.cost = Math.abs(cost) ;
    }

    public void setName(String name) {
        if(name == null){
            this.name = "";
        }else{
            this.name = name;
        }
    }

    public void setDescription(String description) {
        if(description == null){
            this.description = "";
        }else {
            this.description = description;
        }
    }
}
