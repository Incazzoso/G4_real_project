package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.ArtificialIntelligence.IAControl;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen implements Screen {

    private Batch batch;
    private Texture image;
    private Stage stage;
    private GameManager gameManager;
    private DragAndDrop dragAndDrop;
    private Group battleField;

    private ArrayList<CardActor> handCardActors = new ArrayList<>();

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

        //call for sorting the deck at the start of the game
        sortHandCards(gameManager.getPlayer1());


        //TODO: SISTEMARE IL METTERE LE CARTE del secondo giocatore


        // Creazione campo di battaglia
        battleField = new Group();
        battleField.setSize(150,200);
        Texture borderTexture = new Texture("assets/sprite/simple_border.png");
        Image borderImage = new Image(borderTexture);

        borderImage.setSize(battleField.getWidth(), battleField.getHeight());
        battleField.addActor(borderImage);

        battleField.setPosition(Gdx.graphics.getWidth() / 4.0f, (Gdx.graphics.getHeight() / 4.0f) +50); // Posizione sopra la mano
        stage.addActor(battleField);

        // Configura drag and drop
        dragAndDrop.addTarget(new DragAndDrop.Target(battleField) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                // Constrain position to battlefield bounds
                float clampedX = Math.max(0, Math.min(x, battleField.getWidth() - 150));
                float clampedY = Math.max(0, Math.min(y, battleField.getHeight() - 200));
                payload.setDragActor(source.getActor());
                payload.getDragActor().setPosition(clampedX, clampedY);
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                // Constrain final position
                float clampedX = Math.max(0, Math.min(x, battleField.getWidth() - 150));
                float clampedY = Math.max(0, Math.min(y, battleField.getHeight() - 200));

                // Check if there's already a card at this position
                boolean positionOccupied = false;
                for (Actor actor : battleField.getChildren()) {
                    if (actor instanceof CardActor && actor != source.getActor()) {
                        // Check if the new position overlaps with existing cards
                        if (Math.abs(actor.getX() - clampedX) < 150 && Math.abs(actor.getY() - clampedY) < 200) {
                            positionOccupied = true;
                            break;
                        }
                    }
                }

                if (!positionOccupied) {
                    // Remove card from hand
                    gameManager.getPlayer1().getHand().removeCard((Unit) payload.getObject());
                    Unit card = (Unit) payload.getObject();

                    // Create new card actor for battlefield
                    CardActor battleCard = new CardActor(card, dragAndDrop);
                    battleCard.setSize(150, 200);
                    battleCard.setPosition(clampedX, clampedY);
                    battleField.addActor(battleCard);

                    // Update game state
                    gameManager.getGameState().addPlayerOnField(card);

                    // Remove the drag actor and clear the payload
                    payload.setDragActor(null);
                    source.getActor().remove();
                    handCardActors.remove(source.getActor());

                    // Resort hand cards
                    sortHandCards(gameManager.getPlayer1());
                } else {
                    // Return card to hand if position is occupied
                    source.getActor().setPosition(
                        source.getActor().getX(),
                        source.getActor().getY()
                    );
                }
            }
        });
    }


    private void sortHandCards(Player player){
        ArrayList<Unit> handCards = player.getHand().getCards();

        // Remove only hand cards from stage (not battlefield cards)
        for (CardActor cardActor : new ArrayList<>(handCardActors)) {
            cardActor.remove();
        }
        handCardActors.clear();

        // Add each card to the stage
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor = new CardActor(handCards.get(i), dragAndDrop);
            cardActor.setSize(150, 200);
            cardActor.getCard().showBack();
            cardActor.setPosition(Gdx.graphics.getWidth() / 4.0f + (i * 50), 50);
            stage.addActor(cardActor);
            handCardActors.add(cardActor);
        }
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
