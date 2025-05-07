package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.CardActor;
import g4.group.allCardUtilities.DatabaseCard;
import g4.group.allCardUtilities.Unit;
import g4.group.allCardUtilities.MyProfile;

import java.util.ArrayList;

public class EditMenu implements Screen {

    private final Game game;
    private DatabaseCard dbcard;
    private Batch batch;
    private Texture imageExt;
    private Stage stage;
    private Skin texture;
    private Table mainTable;
    private TextButton returnButton;
    private DragAndDrop dragAndDrop;
    private Group deckZone;
    private Group saveZone;
    private Label confirmationLabel;
    private Music music;
    private MyProfile profile;

    public EditMenu(Game game) {
        this.game = game;
        this.profile = new MyProfile(); // Inizializza il profilo del giocatore
    }

    @Override
    public void show() {
        // Setup background music
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/in-the-soul-of-night.mp3"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();

        // Initialize assets
        dbcard = new DatabaseCard();
        batch = new SpriteBatch();
        imageExt = new Texture("assets/sprite/tavolo_build_deck2.png");

        // Setup stage & UI
        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);
        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));

        // Creazione della tavola di carte
        Table scrollableTable = new Table();
        scrollableTable.top().left();
        dragAndDrop = new DragAndDrop();

        int cont = 1;
        for (Unit card : dbcard.getCards()) {
            CardActor cardActor = new CardActor(card, dragAndDrop);

            cardActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Selezionata: " + card.getName());
                }
            });

            scrollableTable.add(cardActor).size(200, 300).pad(10);
            if (cont >= 3) {
                scrollableTable.row();
                cont = 0;
            }
            cont++;
        }

        // ScrollPane per le carte
        ScrollPane scrollPane = new ScrollPane(scrollableTable);
        scrollPane.setScrollingDisabled(true, false);

        // Creazione dell'area di costruzione del mazzo
        deckZone = new Group();
        deckZone.setPosition(100, 500);
        stage.addActor(deckZone);

        // Creazione della zona di salvataggio
        saveZone = new Group();
        saveZone.setPosition(800, 500);
        stage.addActor(saveZone);

        // Configura Drag and Drop per il mazzo
        dragAndDrop.addTarget(new DragAndDrop.Target(deckZone) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Unit card = (Unit) payload.getObject();
                CardActor deckCard = new CardActor(card, dragAndDrop);
                deckCard.setPosition(x, y);
                deckZone.addActor(deckCard);
                System.out.println("Carta aggiunta al mazzo temporaneo: " + card.getName());
            }
        });

        // Configura Drag and Drop per salvare le carte
        dragAndDrop.addTarget(new DragAndDrop.Target(saveZone) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Unit card = (Unit) payload.getObject();
                profile.getMyDeck().addCard(card);
                confirmationLabel.setText("Carta salvata nel profilo: " + card.getName());
                System.out.println("Carta salvata nel profilo!");
            }
        });

        // Messaggio di conferma
        confirmationLabel = new Label("", texture);
        confirmationLabel.setPosition(820, 450);
        stage.addActor(confirmationLabel);

        // Layout principale
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).expand().fill();
        mainTable.setPosition(55, -270);

        // Pulsante di ritorno
        returnButton = new TextButton("Return", texture);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleSelection();
            }
        });

        mainTable.row();
        mainTable.add(returnButton).padTop(20);

        // Aggiunta elementi allo stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        batch.draw(imageExt, 0, 0);
        batch.end();

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
        batch.dispose();
        imageExt.dispose();
        stage.dispose();
        music.dispose();
    }

    public void handleSelection() {
        game.setScreen(new StartMenu(game));
    }
}
