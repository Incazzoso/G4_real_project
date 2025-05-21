package g4.group.gameMechanics;

import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;
import g4.group.allCardUtilities.Effect;
import g4.group.allCardUtilities.MyProfile;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

import java.util.Objects;

public class GameState {
    //PLAYER
    private Player player;
    private Unit[] playerField;
    private int playerDeckSize;

    //ENEMY
    private IAControl enemy;
    private Unit[] enemyField;
    private int enemyDeckSize;

    //FASI GIOCO
    private final GameManager gameManager;
    private final int MAX_SLOT;
    private int currentPhase;
    private int currentTurn;

    public GameState(int numSlots, GameManager gameManager) {
        this.gameManager = gameManager;
        player = new MyProfile().getMyself();
        playerField = new Unit[numSlots];
        enemy = new IAControl(this, gameManager);
        enemyField = new Unit[numSlots];
        this.MAX_SLOT = numSlots;

        for(int i = 0; i<MAX_SLOT; i++){
            playerField[i] = new Unit("",0,0,0,new Effect(false, false),"assets/sprite/simple_border.png");
            enemyField[i] = new Unit("",0,0,0,new Effect(false, false),"assets/sprite/simple_border.png");
        }
    }

    public int getMAX_SLOT() {
        return MAX_SLOT;
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
    public IAControl getEnemy() {
        return enemy;
    }

    public void setEnemy(IAControl enemy) {
        this.enemy = enemy;
    }

    public Unit[] getEnemyField() { return enemyField; }
    public void setEnemyField(Unit[] enemyField) { this.enemyField = enemyField; }
    public int getEnemyDeckSize() { return enemyDeckSize; }
    public void setEnemyDeckSize(int enemyDeckSize) { this.enemyDeckSize = enemyDeckSize; }

    //FASI DEL GIOCO
    public int getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(int phase) { this.currentPhase = phase; }
    public int getCurrentTurn() { return currentTurn; }
    public void incrementTurn() { this.currentTurn++; }

    public void removePlayerUnit(int index){
        if(index < MAX_SLOT){
            playerField[index] = new Unit("",0,0,0,new Effect(false, false),"assets/sprite/simple_border.png");
        }
    }
    public void removeEnemyUnit(int index){
        if(index < MAX_SLOT){
            enemyField[index] = new Unit("",0,0,0,new Effect(false, false),"assets/sprite/simple_border.png");
        }
    }

    // Metodi per la gestione degli slot

    public GameManager getGameManager() {
        return gameManager;
    }

    public boolean isSlotEmpty(int slotIndex, boolean player) {
        return player ? Objects.equals(playerField[slotIndex].getName(), "") : Objects.equals(enemyField[slotIndex].getName(), "");
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

    @Override
    public String toString() {
        return(getUnitToEnemyField(0).getName() + getUnitToEnemyField(1).getName() + getUnitToEnemyField(2).getName());
    }
}
