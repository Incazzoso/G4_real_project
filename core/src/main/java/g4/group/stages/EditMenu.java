package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import g4.group.allCardUtilities.Card;

import java.util.ArrayList;

public class EditMenu implements Screen {

    private Game game;

    public EditMenu(Game game) {
        this.game = game;
    }

    private ArrayList<Card>  allCards = new ArrayList<Card>();
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
    private Image actor= new Image(new Texture("assets/sprite/imgCardo/iloveimg-resized/Bismark.png"));
    private Image actor2= new Image(new Texture("assets/sprite/imgCardo/iloveimg-resized/Leipzig & Nürnberg.png"));
    private Image actor3= new Image(new Texture("assets/sprite/imgCardo/iloveimg-resized/Lützow.png"));
    private Image actor4= new Image(new Texture("assets/sprite/imgCardo/iloveimg-resized/Prinz Eugen.png"));
    private ArrayList<Image> test =new ArrayList<Image>();
    private ArrayList<DragAndDrop> testdrg =new ArrayList<DragAndDrop>();

    @Override
    public void show() {
        int cont;
        batch = new SpriteBatch();
        imageExt= new Texture("assets/sprite/tavolo_build_deck2.png");
        deckimg= new Texture("assets/sprite/card.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        button = new TextButton("Return", texture);
        table.add(button).padTop(-715).padLeft(-625).align(Align.center);
        test.add(actor);
        test.add(actor2);
        test.add(actor3);
        test.add(actor4);
        DragAndDrop dragAndDrop;
        cont=0;
        for (Image I : test) {
            dragAndDrop = new DragAndDrop();
            DragAndDrop finalDragAndDrop = dragAndDrop;
            dragAndDrop.addSource(new DragAndDrop.Source(I) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setDragActor(I);
                    finalDragAndDrop.setDragActorPosition(x,y-400);
                    return payload;
                }

                public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Target target) {
                    float newX = Math.max(0, Math.min(event.getStageX() - I.getWidth() / 2, stage.getWidth() - I.getWidth()));
                    float newY = Math.max(0, Math.min(event.getStageY() - I.getHeight() / 2, stage.getHeight() - I.getHeight()));
                    actor.setPosition(newX, newY);

                    if (actor.getStage() == null) {
                        stage.addActor(I);
                    }
                }
            });
            I.setPosition(x,y);
            stage.addActor(I);
            testdrg.add(dragAndDrop);
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
        batch.dispose();
        image.dispose();
        stage.dispose();
    }

    public void handleSelection() {
        game.setScreen(new StartMenu(game));
    }
}
