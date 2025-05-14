package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
                try {
                    // Constrain final position
                    float clampedX = Math.max(0, Math.min(x, battleField.getWidth() - 150));
                    float clampedY = Math.max(0, Math.min(y, battleField.getHeight() - 200));

                    // Calculate the dropped card's rectangle
                    float droppedCardX = clampedX;
                    float droppedCardY = clampedY;
                    float droppedCardWidth = 150;  // Assuming card width is 150
                    float droppedCardHeight = 200; // Assuming card height is 200

                    // Check for overlap with existing cards
                    boolean positionOccupied = false;
                    for (Actor actor : battleField.getChildren()) {
                        if (actor instanceof CardActor) {
                            CardActor existingCard = (CardActor) actor;

                            // Ensure existingCard has valid dimensions and position
                            if (existingCard != null) {
                                float existingCardX = existingCard.getX();
                                float existingCardY = existingCard.getY();
                                float existingCardWidth = existingCard.getWidth();
                                float existingCardHeight = existingCard.getHeight();

                                // Check for rectangle intersection with a small buffer
                                float overlapBuffer = 10f; // Adjust as needed
                                if (droppedCardX < existingCardX + existingCardWidth - overlapBuffer &&
                                    droppedCardX + droppedCardWidth > existingCardX + overlapBuffer &&
                                    droppedCardY < existingCardY + existingCardHeight - overlapBuffer &&
                                    droppedCardY + droppedCardHeight > existingCardY + overlapBuffer) {
                                    positionOccupied = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!positionOccupied) {
                        // Safely get the card unit from the payload
                        Unit card = null;
                        if (payload != null && payload.getObject() != null) {
                            card = (Unit) payload.getObject();
                        }
                        if (card == null) {
                            Gdx.app.error("GameScreen", "Card is null in payload");
                            return;
                        }

                        // Remove from hand first
                        boolean removed = gameManager.getPlayer1().getHand().removeCard(card);
                        if (!removed) {
                            Gdx.app.error("GameScreen", "Failed to remove card from hand");
                            return;
                        }

                        // Safely remove the source actor
                        if (source != null && source.getActor() != null) {
                            source.getActor().remove();
                            handCardActors.remove(source.getActor());
                        }

                        // Create new card actor for battlefield
                        CardActor battleCard = new CardActor(card, null); // No dragAndDrop for battlefield cards
                        battleCard.setSize(150, 200);
                        battleCard.setPosition(clampedX, clampedY);

                        // Add to battlefield and bring to front
                        battleField.addActor(battleCard);
                        battleCard.toFront();

                        // Update game state
                        gameManager.getGameState().addPlayerOnField(card);

                        // Resort hand cards
                        sortHandCards(gameManager.getPlayer1());
                    } else {
                        // Return card to hand if position is occupied
                        if (source != null && source.getActor() != null) {
                            source.getActor().setPosition(
                                source.getActor().getX(),
                                source.getActor().getY()
                            );
                        }
                    }
                } catch (Exception e) {
                    Gdx.app.error("GameScreen", "Error in drop method", e);
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
            // Only add drag and drop to hand cards
            dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    return null;
                }

                public DragAndDrop.Payload dragStart(DragAndDrop drag, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(cardActor.getCard());
                    payload.setDragActor(cardActor);
                    return payload;
                }
            });
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
