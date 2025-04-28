package g4.group.allCardUtilities.ArtificialIntelligence;

import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAControl {
    private String name;
    private int energy;

    private Hand iaHand;

    private Random rand;

    //TODO: aggiungere collegamento alla classe di gioco

    public IAControl(Hand iaHand) {
        name = "Pippo";
        this.iaHand = iaHand;
        rand = new Random();
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

    //TODO: prende dati dalla classe di gioco e usando lo switch case si fa in modo che l'ia si muove nelle fasi di gioco
    public void IATurn() {
        switch(){
            //DRAW
            case 1:
                drawCard();
                break;
            //MAIN
            case 2:
                mainPhase();
                break;
            //BATTLE
            case 3:
                battlePhase();
                break;
            //END
            case 4:
                endPhase();
                break;
        }
    }

    //PESCA
    public void drawCard() {
        //TODO: richiede funzioni della partita per continuare
        /*if (gameState != null && gameState.getAIDeckSize() > 0) {
            Unit drawnCard = gameState.drawAIcard();
            if (drawnCard != null) {
                iaHand.addCard(drawnCard);
            }
        }*/
    }

    //GIOCA
    public void mainPhase() {
        List<Unit> playableCard = getPlayableCard();

        while (!playableCard.isEmpty() && energy > 0){
            Unit card = selectCard(playableCard);
            if(card != null && card.getCost() <= energy){
                playCard(card);
                playableCard.remove(card);
            }else{
                break;
            }
        }
    }

    //AGGIUNTA CARTA ALLA LISTA
    private List<Unit> getPlayableCard() {
        List<Unit> playable = new ArrayList<>();
        for(Unit unit : iaHand.getCards()){
            if(unit.getCost() <= energy){
                playable.add(unit);
            }
        }
        return playable;
    }

    public void selectCard(List<Unit> card){
        return card.get(rand.nextInt(card.size()));
    }

    public void playCard(Unit card){
        if(card != null && card.getCost() <= energy){
            energy -= card.getCost();
            iaHand.removeCard(card);
            //TODO: richiede funzioni della partita per continuare

        }
    }

    //ATTACCA
    public void battlePhase() {
        //TODO: richiede funzioni della partita per continuare
        /*List<Unit> aiUnits = gameState.getAIUnitsOnField();
        List<Unit> playerUnits = gameState.getPlayerUnitsOnField();*/

        //if (aiUnits.isEmpty()){
        // return
        // };

        randomAttack(/*aiUnits, playerUnits*/);
    }

    public void randomAttack(List<Unit> aiUnits, List<Unit> playerUnits){
        for(Unit attacker : aiUnits){
            if(playerUnits.isEmpty()){
                //TODO: richiede funzioni della partita per continuare
                //gameState.directAttack(attacker.getDamage());
            }else{
                Unit target = playerUnits.get(rand.nextInt(playerUnits.size()));
                attacker.attack(target);
                if(target.getHealth() <= 0){
                    playerUnits.remove(target);
                }
            }
        }
    }

    //FINE TURNO
    public void endPhase(){
        //AGGIUNGERE ENERGIA O NO
    }
}
