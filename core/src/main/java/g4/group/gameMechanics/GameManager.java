package g4.group.gameMechanics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import g4.group.allCardUtilities.*;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;

import java.util.List;
import java.util.Random;

public class GameManager {

    private Stage stage;
    private GameState gameState;
    private boolean currentPlayer;

    public GameManager(Stage stage) {
        this.stage = stage;
        initializeGameState();
        currentPlayer = new Random().nextBoolean();
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

        }
        currentPlayer = !currentPlayer;

        // Update game state after turn change
        if (!currentPlayer) { // If it's now AI's turn, start its turn immediately
            
        }
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
    public void attackPlayer(Unit attacker, boolean player) {
        if(player){
            gameState.getPlayer().setHealth(gameState.getPlayer().getHealth() - attacker.getDamage());
        }
    }
}
