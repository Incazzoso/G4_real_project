package g4.group.allCardUtilities;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;
    private Effect ef;

    public Hand(ArrayList<Card> hand, Effect ef) {
        this.hand = hand;
        this.ef = ef;
    }

    public void addCard(Card card){
        hand.add(card);
    }
    public boolean removeCard(Card card){
        return hand.remove(card);
    }

    public Effect getEf() {
        return ef;
    }
    public String getEff() {
        return ef.toString();
    }

    public void setEf(Effect ef) {
        this.ef = ef;
    }
}
