package g4.group.gameMechanics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import g4.group.allCardUtilities.*;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl; // Import IAControl
import com.badlogic.gdx.utils.Timer; // Import Timer

import java.util.List;
import java.util.Random;

public class GameManager {

    private Stage stage;
    private GameState gameState;
    private boolean currentPlayer;
    private GameScreen gameScreen; // Add a reference to GameScreen

    public GameManager(Stage stage) {
        this.stage = stage;
        initializeGameState();
        currentPlayer = true;
    }


    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    private void initializeGameState() {
        this.gameState = new GameState(5, this); // Initialize first
        gameState.setPlayerDeckSize(8);
        gameState.setEnemyDeckSize(8);
    }

    public void endTurn() {
        if (currentPlayer) {
            gameState.getPlayer().startTurn();
            currentPlayer = false;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (gameScreen != null) {
                        gameScreen.endTurnvisualizer();
                    }
                    gameState.getEnemy().IATurn();
                }
            }, 1.0f);
        } else {
            gameState.getPlayer().startTurn();
            currentPlayer = true;
        }
    }

    public boolean getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Unit drawCard(boolean isPlayerTurn) {
        if (isPlayerTurn) {
            return gameState.getPlayer().getDeck().get(new Random().nextInt(gameState.getPlayer().getDeck().size()));
        } else {
            return gameState.getEnemy().getDeck().get(new Random().nextInt(gameState.getEnemy().getDeck().size()));
        }
    }

    public boolean canPlayCard(Player player, Unit card) {
        return player.getEnergy() >= card.getCost();
    }

    public void attackUnit(Unit attacker, Unit target) {
        attacker.attack(target);
    }

    public void attackPlayer(Unit attacker, boolean isPlayerAttackingHuman) {
        if(isPlayerAttackingHuman){
            gameState.getPlayer().setHealth(gameState.getPlayer().getHealth() - attacker.getDamage());
        } else {
            gameState.getPlayer().setHealth(gameState.getPlayer().getHealth() - attacker.getDamage());
        }
    }
}
