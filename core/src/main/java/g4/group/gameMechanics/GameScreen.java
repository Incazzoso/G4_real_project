package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Batch batch;
    private Texture image;
    private Stage stage;
    private GameManager gameManager;
    private DragAndDrop dragAndDrop;
    private Group battleField;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("assets/sprite/mogano_bg.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Inizializza GameManager
        gameManager = new GameManager(stage);

        // Recupera le carte dalla mano del giocatore
        ArrayList<Unit> handCards = gameManager.getPlayer1().getHand().getCards();
        dragAndDrop = new DragAndDrop();

        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor = new CardActor(handCards.get(i), dragAndDrop);
            cardActor.setSize(150,200);
            cardActor.setPosition(Gdx.graphics.getWidth() / 4.0f + (i * 50), 50); // Posiziona le carte in fila
            stage.addActor(cardActor);
        }

        //TODO: SISTEMARE IL METTERE LE CARTE del secondo giocatore


        // Creazione campo di battaglia
        battleField = new Group();
        battleField.setSize(150,200);
        Texture borderTexture = new Texture("assets/sprite/simple_border.png");
        Image borderImage = new Image(borderTexture);

        borderImage.setSize(battleField.getWidth(), battleField.getHeight());
        battleField.addActor(borderImage);

        battleField.setPosition(Gdx.graphics.getWidth() / 4.0f, Gdx.graphics.getHeight() / 4.0f); // Posizione sopra la mano
        stage.addActor(battleField);

        // Configura drag and drop
        dragAndDrop.addTarget(new DragAndDrop.Target(battleField) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true; // Conferma che la carta può essere rilasciata qui
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Unit card = (Unit) payload.getObject();
                CardActor battleCard = new CardActor(card, dragAndDrop);
                battleCard.setPosition(x, y);
                battleField.addActor(battleCard);
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.draw(image, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        image.dispose();
        stage.dispose();
    }
}
