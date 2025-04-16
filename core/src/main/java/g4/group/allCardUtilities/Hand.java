package g4.group.allCardUtilities;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Unit> hand = new ArrayList<Unit>();

    public Hand(ArrayList<Unit> hand, Effect ef) {
        this.hand = hand;
    }
    public void addCard(Unit card){
        hand.add(card);
    }
    public boolean removeCard(Unit card){
        return hand.remove(card);
    }

}
