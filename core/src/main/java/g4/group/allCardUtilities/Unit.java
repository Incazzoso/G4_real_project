package g4.group.allCardUtilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Unit extends Card {
    private Effect effect;
    private String path;
    private Image image;

    private Image back;
    //costruttori:

    //costruttore normal
    public Unit(String name, int health, int damage, int cost, Effect effect, String imgPath){
        super(name, health, damage, cost);
        this.effect = effect;
        this.path=imgPath;
        this.image=new Image(new Texture(imgPath));
        back=new Image(new Texture("assets/sprite/card.png"));
    }

    //costruttore esteso
    Unit(String name, int health, int damage, int cost, Effect effect, String description,String imgPath) {
        super(name, health, damage, cost, description);
        this.effect = effect;
        this.path=imgPath;
        this.image=new Image(new Texture(imgPath));
        back=new Image(new Texture("assets/sprite/card.png"));
    }

    //costruttore compresso
    Unit(Card card, Effect effect,String imgPath){
        super(card.getName(), card.getHealth(), card.getDamage(), card.getCost(), card.getDescription());
        this.effect = effect;
        this.path=imgPath;
        back=new Image(new Texture("assets/sprite/card.png"));
        try {
            this.image = new Image(new Texture(Gdx.files.internal(imgPath)));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore nel caricamento dell'immagine: " + imgPath);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Image getBack() {
        return back;
    }

    public void showBack(){
        if(image.isVisible() && !back.isVisible()){
            back.setVisible(true);
            image.setVisible(false);
        }else if(!image.isVisible() && back.isVisible()){
            back.setVisible(false);
            image.setVisible(true);
        }
    }

    //procura danno all'unità e restituisce la vita rimanente dell'unità danneggiata
    public int attack(Unit enemy){

        //controllo effetti primari
        if(!effect.canPiercing()){
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
