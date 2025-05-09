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

    //DEFAULT DECK READ A CSV CONTAINING THE IA CARDS
    public void loadIAProfile() {
        try {
            File data = new File("core/src/main/java/g4/group/Data/IADeck.csv");
            if (!data.exists()) {
                System.err.println("Profilo non trovato! Creane uno nuovo.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(data));
            String line = reader.readLine(); // Salta l'intestazione
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(";");

                if (token.length == 8) {
                    String name = token[0];
                    int health = Integer.parseInt(token[1]);
                    int damage = Integer.parseInt(token[2]);
                    int cost = Integer.parseInt(token[3]);
                    String description = token[4];
                    boolean canBurn = Boolean.parseBoolean(token[5]);
                    boolean canPiercing = Boolean.parseBoolean(token[6]);
                    String imgPath = token[7];

                    Effect effect = new Effect(canBurn, canPiercing);
                    Unit card = new Unit(name, health, damage, cost, effect, imgPath);
                    iaHand.addCard(card); // Aggiunge la carta al deck del giocatore
                } else {
                    System.err.println("Errore nel formato della linea CSV: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Errore nel caricamento del profilo!");
        }
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
