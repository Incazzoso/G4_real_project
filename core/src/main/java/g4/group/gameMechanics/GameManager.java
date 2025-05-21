package g4.group.gameMechanics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import g4.group.allCardUtilities.*;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameManager {

    private Stage stage;
    private GameState gameState;
    private boolean currentPlayer;

    public GameManager(Stage stage) {
        this.stage = stage;
        initializeGameState();
        currentPlayer = true;
    }

    private void initializeGameState() {
        this.gameState = new GameState(5, this); // Initialize first
        gameState.setPlayerDeckSize(8);
        gameState.setEnemyDeckSize(8);
    }

    public void endTurn() {
        if (currentPlayer) {
            gameState.getPlayer().startTurn();
        } else {
            gameState.getEnemy().startTurn();
        }
        currentPlayer = !currentPlayer;

    }

    public boolean getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    // Metodi aggiunti per la gestione del campo di battaglia

    public Unit drawCard(boolean player){
        if(player)
            return gameState.getEnemy().getDeck().get(new Random().nextInt(gameState.getEnemy().getDeck().size()));
        else
            return gameState.getPlayer().getDeck().get(new Random().nextInt(gameState.getPlayer().getDeck().size()));
    }

    public boolean canPlayCard(Player player, Unit card) {
        return player.getEnergy() >= card.getCost();
    }

    public void attackUnit(Unit attacker, Unit target) {
        attacker.attack(target);
    }

    public void battlePlayerPhase() {

        for(int i = 0; i < gameState.getMAX_SLOT(); i++){
            if(!Objects.equals(gameState.getUnitToPlayerField(i).getName(), "") && !Objects.equals(gameState.getUnitToEnemyField(i).getName(), "")){
                System.out.println("attacco una carta");
                attackUnit(gameState.getUnitToPlayerField(i), gameState.getUnitToEnemyField(i));
            }else if (!Objects.equals(gameState.getUnitToPlayerField(i).getName(), "") && Objects.equals(gameState.getUnitToEnemyField(i).getName(), "")){
                System.out.println("attacco il nemico");
                attackPlayer(gameState.getUnitToPlayerField(i),false);
            }
        }
    }
    public void attackPlayer(Unit attacker, boolean player) {
        if(player){
            gameState.getPlayer().setHealth(gameState.getPlayer().getHealth() - attacker.getDamage());
            System.out.println(gameState.getPlayer().getHealth());
        }else{
            gameState.getEnemy().setHealth(gameState.getEnemy().getHealth() - attacker.getDamage());
            System.out.println(gameState.getEnemy().getHealth());
        }
    }
}
