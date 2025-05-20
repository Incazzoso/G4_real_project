package g4.group.gameMechanics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;
import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.MyProfile;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

public class GameManager {

    private Player player1, player2;
    private Stage stage;
    private GameState gameState;
    private IAControl playerIA;
    private boolean currentPlayer;

    public GameManager(Stage stage) {
        this.stage = stage;
        player1 = new MyProfile().getMyself();
        player2 = new MyProfile().getMyself(); // Temporaneamente, usa un altro profilo
        playerIA = new IAControl(); // Pass the gameState to the AI
        currentPlayer = true;
        this.gameState = new GameState(5); // Inizializza GameState con il numero di slot
        playerIA.setGameState(this.gameState);
        initializeGameState();
    }

    private void initializeGameState() {
        gameState.setPlayerHealth(20);
        gameState.setPlayerEnergy(2);
        gameState.setPlayerHand(player1.getHand());
        gameState.setPlayerDeckSize(8);

        gameState.setEnemyHealth(20);
        gameState.setEnemyEnergy(4);
        gameState.setEnemyHand(playerIA.getHand());
        gameState.setEnemyDeckSize(8);
    }

    public void endTurn() {
        System.out.println("lololololol");
        if (currentPlayer) {
            player1.startTurn();

        } else {
            playerIA.IATurn();
        }
        currentPlayer = !currentPlayer;
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

    // Metodi aggiunti per la gestione del campo di battaglia

    public boolean isSlotEmpty(int slotIndex) {
        return gameState.isSlotEmpty(slotIndex, 1); // 1 indica il giocatore
    }

    public void placeCardInSlot(Unit card, int slotIndex) {
        if (canPlayCard(player1, card) && gameState.isSlotEmpty(slotIndex, 1)) {
            player1.spendEnergy(card.getCost());
            gameState.addUnitToPlayerBattlefield(card, slotIndex);
            System.out.println("carta posizionata");
        } else {
            System.out.println("non puoi posizionare la carta");
            // Gestire il caso in cui non si può giocare la carta (es., energia insufficiente)
        }
    }

    public boolean canPlayCard(Player player, Unit card) {
        return player.getEnergy() >= card.getCost();
    }

    public int getPlayerHealth() {
        return gameState.getPlayerHealth();
    }

    public int getEnemyHealth() {
        return gameState.getEnemyHealth();
    }

    public int getPlayerEnergy() {
        return gameState.getPlayerEnergy();
    }

    public int getEnemyEnergy() {
        return gameState.getEnemyEnergy();
    }

}
