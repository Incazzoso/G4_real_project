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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.OptionManager;
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
    private final Texture man1 = new Texture("assets/sprite/exagon.png");
    private final Texture man2 = new Texture("assets/sprite/ancor.png");
    private Label hp;
    private Label hp1;
    private final Texture hp2 = new Texture("assets/sprite/heart.png");
    private Label nxt;
    private final Image nextTurn = new Image(new Texture("assets/sprite/next-turn.png"));
    private final List<Group> battleFieldSlots = new ArrayList<>(); // Lista per gli slot
    private final OptionManager opt = new OptionManager();
    private final float CARD_WIDTH = 150;
    private final float CARD_HEIGHT = 200;
    private final float SLOT_WIDTH = 160; // Leggermente più grande della carta
    private final float SLOT_HEIGHT = 210;
    private final int NUM_SLOTS = 5; // Numero di slot
    private final float SLOT_SPACING = 10; // Spazio tra gli slot
    private final float DIM1 = (float) Gdx.graphics.getWidth() /7;
    private final float DIM2 = (float) Gdx.graphics.getWidth() /15;
    private final float DIM3 = (float) Gdx.graphics.getWidth() /20;
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
                if(gameManager.getCurrentPlayer()==true){
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
                        }
                    }, 1.5f);
                }else{
                }
            }
        });

        stage.addActor(nextTurn);
        nxt =new Label("A\nV\nA\nN\nZ\nA",skins);
        nxt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameManager.getCurrentPlayer()==true){
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
                        }
                    }, 1.5f);
                }else{
                }
            }
        });
        nxt.setPosition(848,80);
        nxt.setFontScale(1);
        stage.addActor(nxt);

        // Inizializza GameManager
        gameManager = new GameManager(stage);

        // Recupera le carte dalla mano del giocatore
        dragAndDrop = new DragAndDrop();


        //inizzializzazione campo del player 1
        initializePlayerBattlefield(gameManager.getGameState().getPlayer().getHand());


        //inizzializzazione campo del player IA
        initializeEnemyBattlefield(gameManager.getGameState().getEnemy().getHand());

        if(gameManager.getGameState().getPlayer().getHealth()<=3){
            low.play();
            low.setVolume(opt.getVe());
            low.play();
        }else{
            low.stop();
        }
        stage.addActor(backnxt);
        stage.addActor(txt1);
        stage.addActor(txt2);
        gameManager.getGameState().getPlayer().startTurn();
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
        hp.setText(String.valueOf(gameManager.getGameState().getPlayer().getHealth()));
        hp1.setText(String.valueOf(gameManager.getGameState().getEnemy().getHealth()));
        man.setText(String.valueOf(gameManager.getGameState().getPlayer().getEnergy()));

        // Refresh AI battlefield if it's not player's turn
        if (!gameManager.getCurrentPlayer()) {
            refreshEnemyBattlefield();
        }

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


    private void initializePlayerBattlefield(ArrayList<Unit> handCards) {
        float yPosition = 50;
        float battlefieldY = Gdx.graphics.getHeight() / 4.0f + 30;

        // Initialize hand cards
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor;
            // Player's cards are visible in hand
            cardActor = new CardActor(handCards.get(i), dragAndDrop);

            cardActor.setSize(CARD_WIDTH, CARD_HEIGHT);
            cardActor.setPosition((float) Gdx.graphics.getWidth() /4 + (i*50), yPosition);
            stage.addActor(cardActor);

            // Only enable drag for player 1
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

             // Only enable drop for player 1 slots
            final int slotIndex = i;
            dragAndDrop.addTarget(new DragAndDrop.Target(slot) {
                @Override
                public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                    return gameManager.getGameState().isSlotEmpty(slotIndex,true);
                }

                @Override
                public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {

                    Unit card = (Unit) payload.getObject();
                    if(card.getCost()<=gameManager.getGameState().getPlayer().getEnergy()){
                        CardActor battleCard = new CardActor(card, dragAndDrop);
                        battleCard.setDrawable(card.getImage().getDrawable()); // Make sure to show the actual card
                        battleCard.setSize(CARD_WIDTH, CARD_HEIGHT);
                        battleCard.setPosition((slot.getWidth() - battleCard.getWidth()) / 2,
                            (slot.getHeight() - battleCard.getHeight()) / 2);
                        slot.addActor(battleCard);
                        gameManager.getGameState().addUnitToPlayerField(card, slotIndex);
                        gameManager.getGameState().getPlayer().setEnergy(gameManager.getGameState().getPlayer().getEnergy()-card.getCost());
                        source.getActor().remove();
                    }else{
                    }
                }
            });
        }
    }

    private void initializeEnemyBattlefield(ArrayList<Unit> handCards) {
        float yPosition = Gdx.graphics.getHeight() - 210;
        float battlefieldY = (float) (Gdx.graphics.getHeight() - 460);

        // Initialize hand cards (hidden for the AI)
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
            battleFieldSlots.add(slot);
        }
    }
    private void refreshEnemyBattlefield() {
        float battlefieldY = Gdx.graphics.getHeight() - 460;

        // Clear existing card actors from AI slots
        for (Group slot : battleFieldSlots.subList(NUM_SLOTS, battleFieldSlots.size())) {
            slot.clearChildren();

            // Re-add the border
            Texture borderTexture = new Texture("assets/sprite/simple_border.png");
            Image borderImage = new Image(borderTexture);
            borderImage.setSize(SLOT_WIDTH, SLOT_HEIGHT);
            slot.addActor(borderImage);
        }

        // Add current cards from game state
        for (int i = 0; i < NUM_SLOTS; i++) {
            Group slot = battleFieldSlots.get(NUM_SLOTS + i);

            if (gameManager.getGameState().getEnemyField() != null) {
                for (Unit card : gameManager.getGameState().getEnemyField()) {
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

    /*PLAYER 2
    private void initializePlayer2Battlefield(boolean isPlayer2, ArrayList<Unit> handCards){
        float yPosition = isPlayer2 ? 50 : Gdx.graphics.getHeight() - 210;
        float battlefieldY = isPlayer2 ? Gdx.graphics.getHeight() / 4.0f + 30 : Gdx.graphics.getHeight() / 2 + 50;

        // Initialize hand cards
        for (int i = 0; i < handCards.size(); i++) {
            CardActor cardActor;
            if (isPlayer2) {
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

            if (isPlayer2) { // Only enable drag for player 2
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

            if (isPlayer2) { // Only enable drop for player 1 slots
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
    }*/
    public void endTurnvisualizer(){
        if(!gameManager.getCurrentPlayer()) {
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
                }
            }, 1.5f);
        }
    }
}

