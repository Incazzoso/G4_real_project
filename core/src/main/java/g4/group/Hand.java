package g4.group;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;

    Hand(){
        hand = new ArrayList<Card>();
    }

    public void addCard(Card card){
        hand.add(card);
    }
    public boolean removeCard(Card card){
        return hand.remove(card);
    }
}
