package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.OptionManager;
import g4.group.allCardUtilities.Player;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;
import java.util.List; // Ensure java.util.List is imported

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
    private Label nxt;
    private Image nextTurn = new Image(new Texture("assets/sprite/next-turn.png"));
    private List<Group> battleFieldSlots = new ArrayList<>(); // List for slots
    private OptionManager opt = new OptionManager();
    private final float CARD_WIDTH = 150;
    private final float CARD_HEIGHT = 200;
    private final float SLOT_WIDTH = 160; // Slightly larger than the card
    private final float SLOT_HEIGHT = 210;
    private final int NUM_SLOTS = 5; // Number of slots
    private final float SLOT_SPACING = 10; // Space between slots
    private final float DIM1 = Gdx.graphics.getWidth()/7;
    private final float DIM2 = Gdx.graphics.getWidth()/15;
    private final float DIM3 = Gdx.graphics.getWidth()/20;
    private Image backnxt=new Image(new Texture("assets/sprite/border.png"));
    private Image txt1=new Image(new Texture("assets/sprite/nxt1.png"));
    private Image txt2=new Image(new Texture("assets/sprite/nxt2.png"));
    private Music music;
    private Music eff = Gdx.audio.newMusic(Gdx.files.internal("assets/music/effect/fire.mp3"));
    private Music low = Gdx.audio.newMusic(Gdx.files.internal("assets/music/effect/low_Health_Music.mp3"));

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

        backnxt.setVisible(false);
        txt1.setVisible(false);
        txt2.setVisible(false);
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
        backnxt.setPosition(0,450);
        backnxt.setSize(1024,135);
        nextTurn.setPosition(820,50);
        nextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eff.play();
                eff.setVolume(opt.getVe());
                backnxt.setPosition(0,450);
                backnxt.setVisible(true);
                txt1.setPosition(100,460);
                txt1.setVisible(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        System.out.println("vivviviviviviv");
                        backnxt.setVisible(false);
                        txt1.setVisible(false);
                        gameManager.endTurn();
                        refreshPlayerBattlefield(); // Refresh player battlefield after player's turn
                        refreshIABattlefield(); // Refresh AI battlefield
                    }
                }, 1.5f);
            }
        });

        stage.addActor(nextTurn);
        nxt =new Label("A\nV\nA\nN\nZ\nA",skins);
        nxt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eff.play();
                eff.setVolume(opt.getVe());
                backnxt.setPosition(0, 450);
                backnxt.setVisible(true);
                txt1.setPosition(100, 460);
                txt1.setVisible(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        System.out.println("vivviviviviviv");
                        backnxt.setVisible(false);
                        txt1.setVisible(false);
                        gameManager.endTurn();
                        refreshPlayerBattlefield(); // Refresh player battlefield after AI's turn
                        refreshIABattlefield(); // Also refresh AI's battlefield
                    }
                }, 1.5f);
            }
        });
        nxt.setPosition(848,80);
        nxt.setFontScale(1);
        stage.addActor(nxt);

        // Initialize GameManager
        gameManager = new GameManager(stage);

        // Retrieve cards from player's hand
        dragAndDrop = new DragAndDrop();


        // Initialize player 1's field
        initializeBattlefield(true, gameManager.getPlayer1().getHand().getCards());


        // Initialize IA player's field
        initializeIABattlefield(false, gameManager.getPlayerIA().getHand().getCards());

        if(gameManager.getPlayerHealth()<=3){
            low.play();
            low.setVolume(opt.getVe());
            low.play();
        }else{
            low.stop();
        }
        stage.addActor(backnxt);
        stage.addActor(txt1);
        stage.addActor(txt2);
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

        // Update UI elements
        hp.setText(String.valueOf(gameManager.getPlayerHealth()));
        hp1.setText(String.valueOf(gameManager.getEnemyHealth()));
        man.setText(String.valueOf(gameManager.getPlayerEnergy()));

        // This conditional refresh in render is generally not ideal for performance.
        // It's better to trigger refresh only when the game state actually changes (e.g., after an attack).
        // For now, we'll keep it here, but prioritize explicit calls after state changes.
        // if (!gameManager.getCurrentPlayer()) {
        //     refreshIABattlefield();
        // }

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
        eff.dispose();
        low.dispose();
        // Dispose any other textures you create
    }


    private void initializeBattlefield(boolean isPlayer1, ArrayList<Unit> handCards) {
        float yPosition = isPlayer1 ? 50 : Gdx.graphics.getHeight() - 210;
        float battlefieldY = isPlayer1 ? Gdx.graphics.getHeight() / 4.0f + 30 : Gdx.graphics.getHeight() / 2 + 50;

        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor;
            if (isPlayer1) {
                // Player's cards are visible in hand
                cardActor = new CardActor(handCards.get(i), dragAndDrop);
            } else {
                // Enemy's cards are hidden in hand
                Image hiddenCard = handCards.get(i).Hide();
                cardActor = new CardActor(handCards.get(i), dragAndDrop);
                cardActor.setDrawable(hiddenCard.getDrawable());
            }

            cardActor.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardActor.setPosition((float) Gdx.graphics.getWidth() /4 + (i*50), yPosition);
            stage.addActor(cardActor);

            if (isPlayer1) { // Only enable drag for player 1
                dragAndDrop.addSource(new DragAndDrop.Source(cardActor) {
                    @Override
                    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        DragAndDrop.Payload payload = new DragAndDrop.Payload();
                        payload.setObject(cardActor.getCard());
                        Image img = new Image(cardActor.getDrawable()); // Use the actual drawable
                        img.setSize(CARD_WIDTH, CARD_HEIGHT);
                        payload.setDragActor(img);
                        cardActor.setVisible(false); // Hide original while dragging
                        return payload;
                    }

                    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                        if (target == null) {
                            cardActor.setVisible(true); // Bring back if not dropped
                        }
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
            battleFieldSlots.add(slot); // Add to the common battlefieldSlots list

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
                        battleCard.setDrawable(card.getImage().getDrawable()); // Make sure to show the actual card
                        battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                        battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                            (slot.getHeight() - battleCard.getHeight()) / 2);
                        slot.addActor(battleCard);
                        gameManager.placeCardInSlot(card, slotIndex);
                        source.getActor().remove();
                    }
                });
            }
        }
    }

    private void initializeIABattlefield(boolean isPlayer2, ArrayList<Unit> handCards) {
        float yPosition = isPlayer2 ? 50 : Gdx.graphics.getHeight() - 210;
        float battlefieldY = isPlayer2 ? Gdx.graphics.getHeight() / 4.0f + 30 : Gdx.graphics.getHeight() - 460;

        // Initialize hand cards (hidden for the AI)
        // Similar note as initializeBattlefield for hand card actor creation.
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor;
            Image hiddenCard = handCards.get(i).Hide();
            cardActor = new CardActor(handCards.get(i), dragAndDrop);
            cardActor.setDrawable(hiddenCard.getDrawable());
            cardActor.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardActor.setPosition((float) Gdx.graphics.getWidth() / 4 + (i * 50), yPosition);
            stage.addActor(cardActor);
        }

        // Initialize battlefield slots for the AI at the top
        Texture borderTexture = new Texture("assets/sprite/simple_border.png");
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = new Group();
            slot.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
            slot.setPosition(100 + (i * (SLOT_WIDTH + SLOT_SPACING)), battlefieldY);
            stage.addActor(slot);
            battleFieldSlots.add(slot); // Add to the common battlefieldSlots list

            // Check if there's already a card in this slot (from GameState)
            List<Unit> enemyUnits = gameManager.getGameState().getEnemyUnitsInSlot(i);
            if (!enemyUnits.isEmpty()) {
                for (Unit card : enemyUnits) {
                    CardActor battleCard = new CardActor(card, dragAndDrop);
                    battleCard.setDrawable(card.getImage().getDrawable());
                    battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                    battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                        (slot.getHeight() - battleCard.getHeight()) / 2);
                    slot.addActor(battleCard);
                }
            }
            // Removed drag and drop target for AI, as AI card placement is handled by IAControl.
        }
    }

    public void refreshPlayerBattlefield() {
        // Clear existing cards from player battlefield slots (first NUM_SLOTS elements)
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = battleFieldSlots.get(i); // Player slots are 0 to NUM_SLOTS-1
            slot.clearChildren(); // Remove all actors from the group
            // Re-add the border image
            Texture borderTexture = new Texture("assets/sprite/simple_border.png");
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
        }

        // Add current cards from game state to player battlefield slots
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = battleFieldSlots.get(i); // Player slots are 0 to NUM_SLOTS-1
            List<Unit> playerUnits = gameManager.getGameState().getPlayerUnitsInSlot(i);

            if (!playerUnits.isEmpty()) {
                for (Unit card : playerUnits) {
                    // Create a new CardActor for each unit still in the GameState
                    CardActor battleCard = new CardActor(card, dragAndDrop);
                    battleCard.setDrawable(card.getImage().getDrawable());
                    battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                    battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                        (slot.getHeight() - battleCard.getHeight()) / 2);
                    slot.addActor(battleCard);
                }
            }
        }
    }


    private void refreshIABattlefield() {
        // Clear existing card actors from AI slots (from NUM_SLOTS to the end of the list)
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = battleFieldSlots.get(NUM_SLOTS + i); // Enemy slots are NUM_SLOTS to 2*NUM_SLOTS-1
            slot.clearChildren(); // Remove all actors from the group

            // Re-add the border
            Texture borderTexture = new Texture("assets/sprite/simple_border.png");
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
        }

        // Add current cards from game state to AI battlefield slots
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = battleFieldSlots.get(NUM_SLOTS + i);
            List<Unit> enemyUnits = gameManager.getGameState().getEnemyUnitsInSlot(i);

            if (!enemyUnits.isEmpty()) {
                for (Unit card : enemyUnits) {
                    CardActor battleCard = new CardActor(card, dragAndDrop);
                    battleCard.setDrawable(card.getImage().getDrawable());
                    battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                    battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                        (slot.getHeight() - battleCard.getHeight()) / 2);
                    slot.addActor(battleCard);
                }
            }
        }
    }

    public void endTurnvisualizer(){
        if(gameManager.getCurrentPlayer()==false) {
            eff.play();
            eff.setVolume(opt.getVe());
            backnxt.setPosition(0, 450);
            backnxt.setVisible(true);
            txt1.setPosition(100, 460);
            txt1.setVisible(true);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    System.out.println("vivviviviviviv");
                    backnxt.setVisible(false);
                    txt1.setVisible(false);
                    gameManager.endTurn();
                    refreshPlayerBattlefield(); // Call refresh after AI's turn is complete
                    refreshIABattlefield(); // Also refresh AI's battlefield in case AI played/lost cards
                }
            }, 1.5f);
        }
    }
}
