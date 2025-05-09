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

    public GameState() {
        playerField = new ArrayList<>();
        enemyField = new ArrayList<>();
        currentPhase = 1;
        currentTurn = 1;
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
        return playerField;
    }

    public void setPlayerUnitsOnField(List<Unit> playerUnitsOnField) {
        this.playerField = playerUnitsOnField;
    }

    public int getPlayerDeckSize() {
        return playerDeckSize;
    }

    public void setPlayerDeckSize(int playerDeckSize) {
        this.playerDeckSize = playerDeckSize;
    }

    //ENEMY METHODS
    public int getEnemyHealth(){
        return  enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth){
        this.enemyHealth = enemyHealth;
    }

    public int getEnemyEnergy(){
        return  enemyEnergy;
    }

    public void setEnemyEnergy(int enemyEnergy){
        this.enemyEnergy = enemyEnergy;
    }

    public Hand getEnemyHand(){
        return  enemyHand;
    }

    public void setEnemyHand(Hand enemyHand){
        this.enemyHand = enemyHand;
    }

    public List<Unit> getEnemyField(){
        return  enemyField;
    }

    public void setEnemyField(List<Unit> enemyUnitOnField){
        this.enemyField = enemyUnitOnField;
    }

    public int getEnemyDeckSize(){
        return  enemyDeckSize;
    }

    public void setEnemyDeckSize(int enemyDeckSize){
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

    public void addPlayerOnField(Unit card){
        playerField.add(card);
    }

    public void removePlayerOnField(Unit card){
        playerField.remove(card);
    }

    public void directAttackPlayer(int damage){
        enemyHealth -= damage;
    }

    //ENEMY ACTIONS
    public Unit drawEnemycard(){
        if(enemyDeckSize > 0){
            enemyDeckSize--;
            return  null;
        }
        return null;
    }

    public void addEnemyOnField(Unit card){
        enemyField.add(card);
    }

    public void removeEnemyOnField(Unit card){
        enemyField.remove(card);
    }

    //ATTACCO
    public void directAttackEnemy(int damage){
        playerHealth -= damage;
    }


}
