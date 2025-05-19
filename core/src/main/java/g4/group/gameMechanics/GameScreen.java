package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.OptionManager;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {

    private Batch batch;
    private Texture image;
    private Stage stage;
    private GameManager gameManager;
    private DragAndDrop dragAndDrop;
    private Label man;
    private Texture man1 = new Texture("assets/sprite/exagon.png");
    private Texture man2 = new Texture("assets/sprite/ancor.png");
    private Label hp;
    private Label hp1;
    private Texture hp2 = new Texture("assets/sprite/heart.png");
    private Image nextTurn = new Image(new Texture("assets/sprite/next-turn.png"));
    private List<Group> battleFieldSlots = new ArrayList<>(); // Lista per gli slot
    private OptionManager opt = new OptionManager();
    private final float CARD_WIDTH = 150;
    private final float CARD_HEIGHT = 200;
    private final float SLOT_WIDTH = 160; // Leggermente più grande della carta
    private final float SLOT_HEIGHT = 210;
    private final int NUM_SLOTS = 5; // Numero di slot
    private final float SLOT_SPACING = 10; // Spazio tra gli slot
    private final float DIM1 = Gdx.graphics.getWidth()/7;
    private final float DIM2 = Gdx.graphics.getWidth()/15;
    private final float DIM3 = Gdx.graphics.getWidth()/20;
    private Music music;

    @Override
    public void show() {
        opt.loadData();
        music = Gdx.audio.newMusic(Gdx.files.internal(opt.getSoundPath()));
        music.setVolume(opt.getVm());
        music.setLooping(true);
        music.play();
        batch = new SpriteBatch();
        image = new Texture("assets/sprite/mogano_bg.png");
        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);

        Skin skins = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        man =new Label("4",skins);
        man.setPosition(110,100);
        man.setFontScale(3);
        stage.addActor(man);
        hp =new Label("20",skins);
        hp.setPosition(100,205);
        hp.setFontScale(3);
        stage.addActor(hp);
        hp1 =new Label("20",skins);
        hp1.setPosition(825,848);
        hp1.setFontScale(3);
        stage.addActor(hp1);
        nextTurn.setPosition(820,50);
        stage.addActor(nextTurn);

        // Inizializza GameManager
        gameManager = new GameManager(stage);

        // Recupera le carte dalla mano del giocatore
        ArrayList<Unit> handCards = gameManager.getPlayer1().getHand().getCards();
        dragAndDrop = new DragAndDrop();


        //inizzializzazione campo del player 1
        initializeBattlefield(true, gameManager.getPlayer1().getHand().getCards());

        //inizzializzazione campo del player 2
        initializePlayer2Battlefield(handCards);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.draw(image, 0, 0);
        batch.draw(man1, 65, 150, DIM1, DIM1);
        batch.draw(man1, 790, 790, DIM1, DIM1);
        batch.draw(man1, 65, 45, DIM1, DIM1);
        batch.draw(man2, 104, 82, DIM2, DIM2);
        batch.draw(hp2, 110, 190, DIM3, DIM2);
        batch.draw(hp2, 835, 830, DIM3, DIM2);
        batch.end();


        Gdx.gl.glClearColor(0,0,0,1);
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

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
        man1.dispose();
        man2.dispose();
        // Dispose any other textures you create
    }


    private void initializeBattlefield(boolean isPlayer1, ArrayList<Unit> handCards) {
        float yPosition = isPlayer1 ? 50 : Gdx.graphics.getHeight() - 210;
        float battlefieldY = isPlayer1 ? Gdx.graphics.getHeight() / 4.0f + 30 : Gdx.graphics.getHeight() / 2 + 50;

        // Initialize hand cards
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor = new CardActor(handCards.get(i), dragAndDrop);
            cardActor.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardActor.setPosition((float) Gdx.graphics.getWidth() /4 + (i*50), yPosition);
            stage.addActor(cardActor);

            if (isPlayer1) { // Only enable drag for player 1
                dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(cardActor.getCard());
                        return payload;
                    }

                    public void dragStop(InputEvent event, float x, float y, int pointer) {
                        // Only remove if dropped successfully (would need additional logic)
                    }
                });
            }
        }

        // Initialize battlefield slots
        Texture borderTexture = new Texture("assets/sprite/simple_border.png");
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = new Group();
            slot.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
            slot.setPosition(100 + (i * (SLOT_WIDTH + SLOT_SPACING)), battlefieldY);
            stage.addActor(slot);
            battleFieldSlots.add(slot);

            if (isPlayer1) { // Only enable drop for player 1 slots
                final int slotIndex = i;
                dragAndDrop.addTarget(new DragAndDrop.Target(slot) {
                    @Override
                    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        return gameManager.isSlotEmpty(slotIndex);
                    }

                    @Override
                    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                        Unit card = (Unit) payload.getObject();
                        CardActor battleCard = new CardActor(card, dragAndDrop);
                        battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                        battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                            (slot.getHeight() - battleCard.getHeight()) / 2);
                        slot.addActor(battleCard);
                        gameManager.placeCardInSlot(card, slotIndex);
                        source.getActor().remove(); // Remove card from hand after successful drop
                    }
                });
            }
        }
    }


    private void initializePlayer2Battlefield(ArrayList<Unit> handCards){
        //carte player 1
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor = new CardActor(handCards.get(i), dragAndDrop);
            cardActor.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardActor.setPosition((float) Gdx.graphics.getWidth() /4 + (i*50), Gdx.graphics.getHeight() - 210); // Posiziona le carte in fila
            stage.addActor(cardActor);

            // Configura il drag source per ogni carta
            dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(cardActor.getCard()); // Passa l'oggetto Unit
                    return payload;
                }

                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    cardActor.remove(); // Rimuovi la carta dalla mano dopo il drag
                }
            });
        }

        // Creazione e posizionamento degli slot del campo di battaglia
        Texture borderTexture = new Texture("assets/sprite/simple_border.png");
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = new Group();
            slot.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
            slot.setPosition(100 + (i * (SLOT_WIDTH + SLOT_SPACING)), Gdx.graphics.getHeight()/2 + 50); // Posiziona gli slot in fila
            stage.addActor(slot);
            battleFieldSlots.add(slot);

            // Configura il drag target per ogni slot
            final int slotIndex = i;
            dragAndDrop.addTarget(new DragAndDrop.Target(slot) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    // Accetta il drop solo se lo slot è vuoto
                    return gameManager.isSlotEmpty(slotIndex);
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    Unit card = (Unit) payload.getObject();
                    CardActor battleCard = new CardActor(card, dragAndDrop);
                    battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                    battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2, (slot.getHeight() - battleCard.getHeight()) / 2); // Centra la carta nello slot
                    slot.addActor(battleCard);
                    gameManager.placeCardInSlot(card, slotIndex); // Comunica al GameManager
                }
            });
        }
    }
}

