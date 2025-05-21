package g4.group.gameMechanics;

import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;
import g4.group.allCardUtilities.MyProfile;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

public class GameState {
    //PLAYER
    private Player player;
    private Unit[] playerField;
    private int playerDeckSize;

    //ENEMY
    private Player enemy;
    private Unit[] enemyField;
    private int enemyDeckSize;

    //FASI GIOCO
    private GameManager gameManager;
    private final int MAX_SLOT;
    private int currentPhase;
    private int currentTurn;

    public GameState(int numSlots, GameManager gameManager) {
        this.gameManager = gameManager;
        player = new MyProfile().getMyself();
        playerField = new Unit[5];
        enemy = new IAControl(this, gameManager);
        this.MAX_SLOT = numSlots;
    }

    //PLAYER METHODS
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Unit[] getPlayerField() { return playerField; }
    public void setPlayerField(Unit[] field) { this.playerField = field; }
    public int getPlayerDeckSize() { return playerDeckSize; }
    public void setPlayerDeckSize(int playerDeckSize) { this.playerDeckSize = playerDeckSize; }

    //ENEMY METHODS

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public Unit[] getEnemyField() { return enemyField; }
    public void setEnemyField(Unit[] field) { this.enemyField = field; }
    public int getEnemyDeckSize() { return enemyDeckSize; }
    public void setEnemyDeckSize(int enemyDeckSize) { this.enemyDeckSize = enemyDeckSize; }

    //FASI DEL GIOCO
    public int getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(int phase) { this.currentPhase = phase; }
    public int getCurrentTurn() { return currentTurn; }
    public void incrementTurn() { this.currentTurn++; }

    public void removePlayerUnit(int index){
        if(index < 5){
            playerField[index] = (Unit) null;
        }
    }
    public void removeEnemyUnit(int index){
        if(index < 5){
            enemyField[index] = (Unit) null;
        }
    }

    // Metodi per la gestione degli slot

    public GameManager getGameManager() {
        return gameManager;
    }

    public boolean isSlotEmpty(int slotIndex, boolean player) {
        return player ? playerField[slotIndex] == null : enemyField[slotIndex] == null;
    }

    public void addUnitToPlayerField(Unit unit, int index) {
        if(index < MAX_SLOT) {
            playerField[index] = unit;
        }
    }

    public void addUnitToEnemyField(Unit unit, int index) {
        if(index < MAX_SLOT) {
            enemyField[index] = unit;
        }
    }

    public Unit getUnitToPlayerField(int index) {
        return index < MAX_SLOT ? playerField[index] : null;
    }

    public Unit getUnitToEnemyField(int index) {
        return index < MAX_SLOT ? enemyField[index] : null;
    }

    public int getMAX_SLOT() { return MAX_SLOT; }
}
