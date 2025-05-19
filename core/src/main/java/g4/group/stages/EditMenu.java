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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.*;

import java.util.ArrayList;

public class EditMenu implements Screen {

    private final Game game;
    private MyProfile deck;
    private DatabaseCard dbcard;
    private Batch batch;
    private Texture imageExt;
    private Image deckimg;
    private Stage stage;
    private Skin texture;
    private Table mainTable;
    private TextButton button;
    private ArrayList<Unit> dad = new ArrayList<Unit>();
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/in-the-soul-of-night.mp3"));
    private Music eff = Gdx.audio.newMusic(Gdx.files.internal("assets/music/effect/click-effect.mp3"));
    private OptionManager opt = new OptionManager();
    private boolean scrollVisible = false;
    private ScrollPane scroll;


    public EditMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        opt.loadData();
        // Setup background music
        music.setVolume(opt.getVm());
        music.setLooping(true);
        music.play();

        // Initialize assets
        dbcard = new DatabaseCard();
        batch = new SpriteBatch();
        imageExt = new Texture("assets/sprite/tavolo_build_deck2.png");
        deckimg = new Image(new Texture("assets/sprite/card.png"));

        // Setup stage & skin
        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);
        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        Label lab= new Label("number of cards in the deck:",texture);
        lab.setPosition(650,840);
        deckimg.setPosition(450, 752);
        stage.addActor(deckimg);
        stage.addActor(lab);

        // Create a scrollable table
        Table scrollableTable = new Table();
        scrollableTable.top().left(); // Align properly for scrolling
        int cont =1;
        // Add cards to the scrollable table
        for (Unit I : dbcard.getCards()) {
            I.getImage().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dad.add(I);
                    scrollableTable.removeActor(I.getImage());
                    lab.setText("number of cards in the deck: "+dad.size());
                    eff.play();
                    eff.setVolume(opt.getVe());
                }
            });
            scrollableTable.add(I.getImage()).size(275, 375).pad(10);
            if (cont>=3) {
                scrollableTable.row();
                cont=0;
            }
            cont++;
        }
        scrollableTable.row();
        Image im=new Image(imageExt);
        scrollableTable.add(im).size(210, 210).pad(10);
        // Wrap the table inside a scroll pane
        ScrollPane scrollPane = new ScrollPane(scrollableTable);
        scrollPane.setScrollingDisabled(true, false); // Enable both horizontal & vertical scrolling

        // Main layout

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).expand().fill(); // Make the scrollable area fill the available space
        mainTable.setPosition(55,-270);
        // Return button
        button = new TextButton("Return", texture);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eff.play();
                eff.setVolume(opt.getVe());
                handleSelection();
            }
        });
        button.setPosition(180,840);
        stage.addActor(button);
        // Add everything to the stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.draw(imageExt, 0, 0);
        //batch.draw(deckimg, 450, 752);
        batch.end();
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        batch.dispose();
        imageExt.dispose();
        stage.dispose();
        music.dispose();
    }

    public void handleSelection() {
        deck=new MyProfile(new Player("kilos",new Hand(dad)));
        music.dispose();
        game.setScreen(new StartMenu(game));
    }
}
