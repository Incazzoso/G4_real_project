package g4.group.allCardUtilities;

import java.util.ArrayList;

public class Player {
    private final String name;
    private int MAX_energy;
    private int health;
    private int energy;
    private ArrayList<Unit> deck;
    private ArrayList<Unit> hand;


    public Player(String name, ArrayList<Unit> deck){
        this.name = name;
        MAX_energy = 4;
        health = 20;
        energy = MAX_energy;

        if(deck.size() < 10){
            this.deck = deck;
        }
        hand = deck;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Unit> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Unit> deck) {
        this.deck = deck;
    }

    public ArrayList<Unit> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getMAX_energy() {
        return MAX_energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void setMAX_energy(int MAX_energy) {
        this.MAX_energy = MAX_energy;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    // Metodo per spendere energia
    public void spendEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
        } else {
            //TODO: gestire il caso in cui non c'è abbastanza energia (es., lanciare un'eccezione, restituire false, ecc.)
            System.out.println("Energia insufficiente!");
        }
    }

    //inizia il turno rimmetendo l'energia e aggiungendo +1 al massimo
    public void startTurn(){
        MAX_energy++;
        energy = getMAX_energy();
    }

}
