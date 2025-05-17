package g4.group.gameMechanics;

import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    //PLAYER
    private int playerHealth;
    private int playerEnergy;
    private Hand playerHand;
    private List<Unit> playerField;
    private int playerDeckSize;

    //ENEMY
    private int enemyHealth;
    private int enemyEnergy;
    private Hand enemyHand;
    private List<Unit> enemyField;
    private int enemyDeckSize;

    //FASI GIOCO
    private int currentPhase;
    private int currentTurn;

    private List<Unit>[] playerBattlefieldSlots; // Array di liste per gli slot del giocatore
    private List<Unit>[] enemyBattlefieldSlots; // Array di liste per gli slot del nemico
    private final int NUM_SLOTS;

    public GameState(int numSlots) {
        NUM_SLOTS = numSlots;
        playerField = new ArrayList<>();
        enemyField = new ArrayList<>();
        currentPhase = 1;
        currentTurn = 1;

        playerBattlefieldSlots = new ArrayList[NUM_SLOTS];
        enemyBattlefieldSlots = new ArrayList[NUM_SLOTS]; // Inizializza gli slot del nemico
        for (int i = 0; i < NUM_SLOTS; i++) {
            playerBattlefieldSlots[i] = new ArrayList<>(); // Inizializza ogni slot del giocatore
            enemyBattlefieldSlots[i] = new ArrayList<>(); // Inizializza ogni slot del nemico
        }
    }

    //PLAYER METHODS
    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getPlayerEnergy() {
        return playerEnergy;
    }

    public void setPlayerEnergy(int playerEnergy) {
        this.playerEnergy = playerEnergy;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public List<Unit> getPlayerUnitsOnField() {
        List<Unit> allCards = new ArrayList<>();
        for (List<Unit> slot : playerBattlefieldSlots) {
            allCards.addAll(slot);
        }
        return allCards;
    }

    public void setPlayerUnitsOnField(List<Unit> playerField) {
        this.playerField = playerField;
    }

    public int getPlayerDeckSize() {
        return playerDeckSize;
    }

    public void setPlayerDeckSize(int playerDeckSize) {
        this.playerDeckSize = playerDeckSize;
    }

    //ENEMY METHODS
    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public int getEnemyEnergy() {
        return enemyEnergy;
    }

    public void setEnemyEnergy(int enemyEnergy) {
        this.enemyEnergy = enemyEnergy;
    }

    public Hand getEnemyHand() {
        return enemyHand;
    }

    public void setEnemyHand(Hand enemyHand) {
        this.enemyHand = enemyHand;
    }

    public List<Unit> getEnemyUnitsOnField() {
        List<Unit> allCards = new ArrayList<>();
        for (List<Unit> slot : enemyBattlefieldSlots) {
            allCards.addAll(slot);
        }
        return allCards;
    }

    public void setEnemyUnitsOnField(List<Unit> enemyUnitOnField) {
        this.enemyField = enemyUnitOnField;
    }

    public int getEnemyDeckSize() {
        return enemyDeckSize;
    }

    public void setEnemyDeckSize(int enemyDeckSize) {
        this.enemyDeckSize = enemyDeckSize;
    }

    //FASI DEL GIOCO
    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int phase) {
        this.currentPhase = phase;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setIncrementTurn(int currentTurn) {
        this.currentTurn++;
    }

    //PLAYER ACTIONS
    public Unit drawPlayercard() {
        if (playerDeckSize > 0) {
            playerDeckSize--;
            return null;
        }
        return null;
    }

    public void addPlayerOnField(Unit card) {
        playerField.add(card);
    }

    public void removePlayerOnField(Unit card) {
        playerField.remove(card);
    }

    public void directAttackPlayer(int damage) {
        enemyHealth -= damage;
    }

    //ENEMY ACTIONS
    public Unit drawEnemycard() {
        if (enemyDeckSize > 0) {
            enemyDeckSize--;
            return null;
        }
        return null;
    }

    public void addEnemyOnField(Unit card) {
        enemyField.add(card);
    }

    public void removeEnemyOnField(Unit card) {
        enemyField.remove(card);
    }

    public void directAttackEnemy(int damage) {
        playerHealth -= damage;
    }

    // Metodi aggiunti per la gestione degli slot

    public boolean isSlotEmpty(int slotIndex, int player) {
        if (player == 1) {
            return playerBattlefieldSlots[slotIndex].isEmpty();
        } else {
            return enemyBattlefieldSlots[slotIndex].isEmpty();
        }
    }

    public void addUnitToPlayerBattlefield(Unit card, int slotIndex) {
        playerBattlefieldSlots[slotIndex].add(card);
    }

    public void addUnitToEnemyBattlefield(Unit card, int slotIndex) {
        enemyBattlefieldSlots[slotIndex].add(card);
    }

    public List<Unit> getPlayerUnitsInSlot(int slotIndex) {
        return playerBattlefieldSlots[slotIndex];
    }

    public List<Unit> getEnemyUnitsInSlot(int slotIndex) {
        return enemyBattlefieldSlots[slotIndex];
    }

    public void clearPlayerSlot(int slotIndex) {
        playerBattlefieldSlots[slotIndex].clear();
    }

    public void clearEnemySlot(int slotIndex) {
        enemyBattlefieldSlots[slotIndex].clear();
    }
}
