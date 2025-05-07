package g4.group.allCardUtilities;

public class Player {
    private String name;
    private int energy;
    private Hand hand;
    //private Deck deck;

    Player(String name, Hand hand){
        this.name = name;
        if(hand.getCards().size() < 10)
            this.hand = hand;
    }

    public Hand getHand() {
        return hand;
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
