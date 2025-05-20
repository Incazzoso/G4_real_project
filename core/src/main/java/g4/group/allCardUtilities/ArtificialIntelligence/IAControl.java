package g4.group.allCardUtilities.ArtificialIntelligence;

import g4.group.allCardUtilities.Effect;
import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.Unit;
import g4.group.gameMechanics.GameState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAControl {
    private String name;
    private Hand iaHand;
    private int energy;
    GameState gameState;
    private Random rand;

    public IAControl() {
        name = "NavIgor";
        rand = new Random();
        this.energy = 3;
        this.iaHand = loadIAProfile();
    }

    public Hand loadIAProfile() {
        Hand hand = new Hand(new ArrayList<>());
        try {
            File data = new File("core/src/main/java/g4/group/Data/IADeck.csv");
            if (!data.exists()) {
                System.err.println("Profilo non trovato! Creane uno nuovo.");
                return hand;
            }

            BufferedReader reader = new BufferedReader(new FileReader(data));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(";");

                if (token.length == 8) {
                    String name = token[3].trim();
                    int health = Integer.parseInt(token[1].trim());
                    int damage = Integer.parseInt(token[2].trim());
                    int cost = Integer.parseInt(token[3].trim());
                    String description = token[4].trim();
                    boolean canBurn = Boolean.parseBoolean(token[5].trim());
                    boolean canPiercing = Boolean.parseBoolean(token[6].trim());
                    String imgPath = token[7].trim();

                    Effect effect = new Effect(canBurn, canPiercing);
                    Unit card = new Unit(name, health, damage, cost, effect, imgPath);
                    hand.addCard(card);
                } else {
                    System.err.println("Errore nel formato della linea CSV: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del profilo!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Errore di formato numero nel CSV!");
            e.printStackTrace();
        }
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public Hand getHand() {
        return iaHand;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void incrementEnergy(){
        this.energy++;
    }

    public void spendEnergy(int amount) {
        this.energy -= amount;
    }

    public void IATurn() {
        for (int phase = 1; phase <= 4; phase++) {
            gameState.setCurrentPhase(phase);
            switch(phase) {
                case 1: // DRAW
                    drawCard();
                    break;
                case 2: // MAIN
                    mainPhase();
                    break;
                case 3: // BATTLE
                    battlePhase();
                    break;
                case 4: // END
                    endPhase();
                    break;
            }
        }
        gameState.setCurrentPhase(1);
    }

    public void drawCard() {
        if (gameState != null && gameState.getEnemyDeckSize() > 0) {
            Unit drawnCard = gameState.drawEnemycard();
            if (drawnCard != null) {
                iaHand.addCard(drawnCard);
            }
        }
    }

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

    public void playCard(Unit card) {
        if(card != null && card.getCost() <= energy) {
            spendEnergy(card.getCost());
            iaHand.removeCard(card);
            for (int i = 0; i < gameState.getNumSlots(); i++) {
                if (gameState.isSlotEmpty(i, 2)) {
                    gameState.addUnitToEnemyBattlefield(card, i);
                    break;
                }
            }
        }
    }

    public void battlePhase() {
        List<Unit> aiUnits = gameState.getEnemyUnitsOnField();
        List<Unit> playerUnits = gameState.getPlayerUnitsOnField(); // Correctly get a List<Unit> here

        if (aiUnits.isEmpty()){
            return;
        }

        randomAttack(aiUnits, playerUnits); // Pass the List<Unit> to randomAttack
    }

    public void randomAttack(List<Unit> aiUnits, List<Unit> playerUnits){ // Parameter type changed to List<Unit>
        for(Unit attacker : aiUnits){
            if(playerUnits.isEmpty()){
                gameState.directAttackEnemy(attacker.getDamage());
            }else{
                Unit target = playerUnits.get(rand.nextInt(playerUnits.size()));
                attacker.attack(target);
                // This removal logic might be problematic if the original list isn't directly modified
                // It's better to update the GameState after an attack.
                if(target.getHealth() <= 0){
                    // You need a mechanism to remove the unit from the player's battlefield in GameState
                    // For example: gameState.removeUnitFromPlayerBattlefield(target);
                    // This would require a new method in GameState.
                    // For now, let's just assume the health update is sufficient,
                    // and a separate cleanup phase will handle dead units.
                }
            }
        }
    }

    public void endPhase(){
        incrementEnergy();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
