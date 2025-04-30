package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.Card;
import g4.group.allCardUtilities.DatabaseCard;
import g4.group.allCardUtilities.Unit;

import java.util.ArrayList;

public class EditMenu implements Screen {

    private Game game;

    public EditMenu(Game game) {
        this.game = game;
    }

    private DatabaseCard dbcard;
    private Batch batch;
    private Texture image;
    private Texture imageExt;
    private Texture deckimg;
    private  Stage stage;
    private  Skin texture;
    private  Table table;
    TextButton button;
    private int x=30;
    private int y=350;
    int cont = 0;
    private ArrayList<DragAndDrop> dad =new ArrayList<DragAndDrop>();
    Music music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/in-the-soul-of-night.mp3"));

    @Override
    public void show() {
        music.setVolume(0.5f);                 // sets the volume to half the maximum volume
        music.setLooping(true);
        music.play();
        dbcard=new DatabaseCard();
        batch = new SpriteBatch();
        imageExt= new Texture("assets/sprite/tavolo_build_deck2.png");
        deckimg= new Texture("assets/sprite/card.png");
        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        button = new TextButton("Return", texture);
        table.add(button).padTop(-715).padLeft(-625).align(Align.center);
        DragAndDrop dragAndDrop;
        cont=0;
        for (Unit I : dbcard.getCards()) {
            dragAndDrop = new DragAndDrop();
            DragAndDrop finalDragAndDrop = dragAndDrop;
            dragAndDrop.addSource(new DragAndDrop.Source(I.getImage()) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setDragActor(I.getImage());
                    finalDragAndDrop.setDragActorPosition(x,y-400);
                    return payload;
                }

                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Target target) {
                    float newX = Math.max(0, Math.min(event.getStageX() - I.getImage().getWidth() / 2, stage.getWidth() - I.getImage().getWidth()));
                    float newY = Math.max(0, Math.min(event.getStageY() - I.getImage().getHeight() / 2, stage.getHeight() - I.getImage().getHeight()));
                    I.getImage().setPosition(newX, newY);

                    if (I.getImage().getStage() == null) {
                        stage.addActor(I.getImage());
                    }
                }
            });
            I.getImage().setPosition(x,y);
            stage.addActor(I.getImage());
            dad.add(dragAndDrop);
            if(cont == 2){
                cont=0;
                x=30;
                y=y-350;
            }else{
                cont++;
                x=x+340;
            }

        }

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleSelection();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.draw(imageExt, 0, 0);
        batch.draw(deckimg, 450,752 );
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
        batch.dispose();
        image.dispose();
        stage.dispose();
        music.dispose();
    }

    public void handleSelection() {
        game.setScreen(new StartMenu(game));
    }
}
