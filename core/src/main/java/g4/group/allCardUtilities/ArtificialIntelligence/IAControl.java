package g4.group.allCardUtilities.ArtificialIntelligence;

import g4.group.allCardUtilities.Effect;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;
import g4.group.gameMechanics.GameManager;
import g4.group.gameMechanics.GameState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAControl extends Player{
    GameState gameState;
    GameManager gameManager;
    private final Random rand;

    public IAControl( GameState gameState) {
        super("navigor", gameState.getPlayer().getHand());
        this.gameState = gameState;
        rand = new Random();
    }

    public void loadIAProfile() {
        try {
            File data = new File("core/src/main/java/g4/group/Data/IADeck.csv");
            if (!data.exists()) {
                System.err.println("Profilo non trovato! Creane uno nuovo.");
                return;
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
                    gameState.getEnemy().getDeck().add(card);
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
    }

    public void IATurn() {
        for (int phase = 1; phase <= 4; phase++) {
            gameState.setCurrentPhase(phase);
            switch(phase) {
                case 1: // DRAW
                    drawPhase();
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

    public void drawPhase() {
        if (gameState != null && gameState.getEnemyDeckSize() > 0) {
            Unit drawnCard = gameManager.drawCard(false);
            if (drawnCard != null) {
                gameState.getEnemy().getHand().add(drawnCard);
            }
        }
    }

    public void mainPhase() {
        while (gameState.getEnemy().getHealth() > 0 && !gameState.getEnemy().getHand().isEmpty() && gameState.getEnemy().getEnergy() > 0){
            for(int i = 0; i < gameState.getEnemy().getHand().size(); i++){

            }
        }
    }

    public void battlePhase() {


        for(int i = 0; i < gameState.getMAX_SLOT(); i++){
            if(gameState.getUnitToEnemyField(i) != null && gameState.getUnitToPlayerField(i) != null)
                gameManager.attackUnit(gameState.getUnitToEnemyField(i), gameState.getUnitToPlayerField(i));
                else
                    gameManager.attackPlayer(gameState.getUnitToEnemyField(i),false);
        }
    }
    public void endPhase(){

    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getAI() {
        return gameState.getEnemy();
    }

    private ArrayList<Unit> getPlayableCard() {
        ArrayList<Unit> playable = new ArrayList<>();
        for(Unit unit : gameState.getEnemy().getHand()){
            if(unit.getCost() <= gameState.getEnemy().getEnergy()){
                playable.add(unit);
            }
        }
        return playable;
    }

    private Unit selectCard(List<Unit> playableCard){
        return playableCard.get(rand.nextInt(playableCard.size()));
    }

    private void playCard(Unit card) {
        if(card != null && card.getCost() <= gameState.getEnemy().getEnergy()) {
            gameState.getEnemy().setEnergy(gameState.getEnemy().getEnergy() - card.getCost());
            for (int i = 0; i < gameState.getMAX_SLOT(); i++) {
                if (gameState.isEmpty(i, false)) {
                    gameState.addUnitToEnemyField(card,i);
                    break;
                }
            }
        }
    }
}
