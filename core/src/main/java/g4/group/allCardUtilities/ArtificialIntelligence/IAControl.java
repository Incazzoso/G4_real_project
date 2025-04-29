package g4.group.allCardUtilities.ArtificialIntelligence;

import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.Unit;
import g4.group.stages.State.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAControl {
    private String name;
    private Hand iaHand;

    private int energy;

    GameState gameState;

    private Random rand;

    public IAControl(Hand iaHand, GameState gameState) {
        name = "NavIgor";
        this.iaHand = iaHand;
        this.gameState = gameState;
        rand = new Random();
        this.energy = 3;
    }

    //ENERGIA
    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void incrementEnergy(){
        this.energy++;
    }

    //FASI DI GIOCO
    public void IATurn() {
        switch(gameState.getCurrentPhase()){
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
        if (gameState != null && gameState.getEnemyDeckSize() > 0) {
            Unit drawnCard = gameState.drawEnemycard();
            if (drawnCard != null) {
                iaHand.addCard(drawnCard);
            }
        }
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
    public List<Unit> getPlayableCard() {
        List<Unit> playable = new ArrayList<>();
        for(Unit unit : iaHand.getCards()){
            if(unit.getCost() <= energy){
                playable.add(unit);
            }
        }
        return playable;
    }

    public Unit selectCard(List<Unit> playableCard){
        return playableCard.get(rand.nextInt(playableCard.size()));
    }

    public void playCard(Unit card){
        if(card != null && card.getCost() <= energy){
            energy -= card.getCost();
            iaHand.removeCard(card);
            gameState.addEnemyOnField(card);
        }
    }

    //ATTACCA
    public void battlePhase() {
        List<Unit> aiUnits = gameState.getEnemyField();
        List<Unit> playerUnits = gameState.getPlayerUnitsOnField();

        if (aiUnits.isEmpty()){
         return;
        };

        randomAttack(aiUnits, playerUnits);
    }

    public void randomAttack(List<Unit> aiUnits, List<Unit> playerUnits){
        for(Unit attacker : aiUnits){
            if(playerUnits.isEmpty()){
                gameState.directAttackEnemy(attacker.getDamage());
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
        incrementEnergy();
    }
}
