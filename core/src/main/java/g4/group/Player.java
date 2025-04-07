package g4.group;

public class Player {
    private String name;
    private int energy;
    private Hand hand;
    //private Deck deck;

    Player(String name, Hand hand){
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
