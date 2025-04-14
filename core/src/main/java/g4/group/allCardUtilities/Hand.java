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
    public void endturncontroller(){
        for (Unit i : hand) {
            if(i.getEf().isisOnFire()==true){
                i.setHealth(i.getHealth()-1);
                i.setFirecount(i.getFirecount()-1);
                if(i.getFirecount()<=0){
                    i.getEf().setIsOnFire(false);
                    i.setFirecount(3);
                }else{
                }
            }else{
                if(i.getEf().isisSinkeing()==true){
                    i.setHealth(i.getHealth()-1);
                    i.setImcount(i.getImcount()-1);
                    if(i.getImcount()<=0){
                        i.getEf().setisSinkeing(false);
                        i.setImcount(3);
                    }else{
                    }
                }else{
                }
            }
        }
    }
}
