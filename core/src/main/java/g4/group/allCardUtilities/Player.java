package g4.group.allCardUtilities;

public class Player {
    private String name;
    private int MAX_energy;
    private int energy;
    private Hand hand;
    //private Deck deck;

    public Player(String name, Hand hand){
        this.name = name;
        MAX_energy = 1;
        energy = MAX_energy;
        if(hand.getCards().size() < 10)
            this.hand = hand;
    }

    public Hand getHand() {
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
