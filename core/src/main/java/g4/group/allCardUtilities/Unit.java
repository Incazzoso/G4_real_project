package g4.group.allCardUtilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Unit extends Card {
    private Effect effect;
    private Texture texture;
    private Image image;

    //costruttori:

    //costruttore normal
    Unit(String name, int health, int damage, int cost, Effect effect){
        super(name, health, damage, cost);
        this.effect = effect;
    }

    //costruttore esteso
    Unit(String name, int health, int damage, int cost, Effect effect, String description) {
        super(name, health, damage, cost, description);
        this.effect = effect;
    }

    //costruttore compresso
    Unit(Card card, Effect effect){
        super(card.getName(), card.getHealth(), card.getDamage(), card.getCost(), card.getDescription());
        this.effect = effect;
    }




    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }


    //procura danno all'unità e restituisce la vita rimanente dell'unità danneggiata
    public int attack(Unit enemy){

        //controllo effetti primari
        if(effect.canPiercing()){
            enemy.setHealth(enemy.getHealth() - this.getDamage());
        }else {
            enemy.setHealth(enemy.getHealth() - this.getDamage() * 2);
        }

        //controllo effetti secondari
        if(effect.isOnFire()){
            enemy.setHealth(enemy.getHealth() - 1);
        }
        return enemy.getHealth();
    }
}
