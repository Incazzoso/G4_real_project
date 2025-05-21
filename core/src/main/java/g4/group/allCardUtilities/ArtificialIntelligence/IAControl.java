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

    public IAControl(GameState gameState, GameManager gameManager) {
        super("navigor", gameState.getPlayer().getDeck());
        this.gameState = gameState;
        this.gameManager = gameManager;
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
                    startPhase();
                    System.out.println("fne draw azione");
                    break;
                case 2: // MAIN
                    mainPhase();
                    System.out.println("fne main azione");
                    break;
                case 3: // BATTLE
                    battlePhase();
                    System.out.println("fne battle azione");
                    break;
                case 4: // END
                    endPhase();
                    System.out.println("fne end azione");
                    break;
            }
        }
        gameState.setCurrentPhase(1);
    }

    public void startPhase() {
        if (gameState.getEnemyDeckSize() > 0) {
            Unit drawnCard = gameManager.drawCard(false);
            if (drawnCard != null) {
                gameState.getEnemy().getHand().add(drawnCard);
            }
        }
        gameState.getEnemy().setMAX_energy(gameState.getEnemy().getMAX_energy() + 1);
        gameState.getEnemy().setEnergy(gameState.getEnemy().getMAX_energy());
    }

    public void mainPhase() {
        while (gameState.getEnemy().getHealth() > 0 && !gameState.getEnemy().getHand().isEmpty() && gameState.getEnemy().getEnergy() > 0){  //se puoi fa il turno
            for(int i = 0; i < gameState.getEnemy().getHand().size(); i++){                                                                 //controlla la mano
                if(gameState.getEnemy().getHand().get(i).getCost() < gameState.getEnemy().getEnergy()){                                     //se trovi una carta poco costosa
                    for(int j = 0; j < gameState.getMAX_SLOT(); j++){                                                                       //controlla se hai spazio
                        if(gameState.isSlotEmpty(i,false)){                                                                          //e se hai spazio
                            gameState.getEnemy().setEnergy(getEnergy()-gameState.getEnemy().getHand().get(i).getCost());                    //spendi energia
                            gameState.addUnitToEnemyField(gameState.getEnemy().getHand().get(i),i);                                         //posizionala nello spazio
                        }
                    }
                }
            }
        }
    }

    public void battlePhase() {
        for(int i = 0; i < gameState.getMAX_SLOT(); i++){
            if(gameState.getUnitToEnemyField(i) != null && gameState.getUnitToPlayerField(i) != null){
                gameManager.attackUnit(gameState.getUnitToEnemyField(i), gameState.getUnitToPlayerField(i));
            }else if (gameState.getUnitToEnemyField(i) != null && gameState.getUnitToPlayerField(i) == null){
                gameManager.attackPlayer(gameState.getUnitToEnemyField(i),true);
            }
        }
    }

    public void endPhase(){
        gameManager.endTurn();
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
                if (gameState.isSlotEmpty(i, false)) {
                    gameState.addUnitToEnemyField(card,i);
                    break;
                }
            }
        }
    }
}
