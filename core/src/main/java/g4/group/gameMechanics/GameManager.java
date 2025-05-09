package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;
import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.MyProfile;
import g4.group.allCardUtilities.Player;

//gestisce il game mechanics
public class GameManager {

    //i player e che esegue il turno
    private Player player1, player2;
    private Stage stage;

    //GAMESTATE
    private GameState gameState;

    //IAControl
    private IAControl playerIA;

    //CHI HA IL TURNO
    private boolean currentPlayer;

    public GameManager(Stage stage) {
        this.stage = stage;
        player1 = new MyProfile().getMyself();
        //player2 = new Player("Giocatore 2", new Hand());   TEMPORANEAMENTE USERò UN ALTRO PROFILO FINO A QUANDO NON SI AGGIUNGERANNO I BOT
        player2 = new MyProfile().getMyself();

        //IAControl
        playerIA = new IAControl();

        currentPlayer = true; // true = player 1 && false = player IA

        this.gameState = new GameState();
        initializeGameState();
    }

    //SET GAMESTATE DATA
    private void initializeGameState() {
        // Set initial player values
        gameState.setPlayerHealth(30);
        gameState.setPlayerEnergy(1);
        gameState.setPlayerHand(player1.getHand());
        gameState.setPlayerDeckSize(20); // Example deck size

        // Set initial enemy values
        gameState.setEnemyHealth(30);
        gameState.setEnemyEnergy(1);
        gameState.setEnemyHand(playerIA.getHand());
        gameState.setEnemyDeckSize(20); // Example deck size
    }

    public void endTurn() {

        if(currentPlayer == true) {
            player1.startTurn();
        }else{
            playerIA.IATurn();
        }

        if(currentPlayer == true) {
            currentPlayer = false;
        }else{
            currentPlayer = true;
        }

        gameState.setIncrementTurn(gameState.getCurrentTurn());
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public IAControl getPlayerIA() {
        return playerIA;
    }

    public boolean getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }
}
