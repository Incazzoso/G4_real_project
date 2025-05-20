package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.OptionManager;

import java.text.DecimalFormat;

public class IntermedumMenu implements Screen {
    private Game game;

    public IntermedumMenu(Game game) {
        this.game = game;
    }

    private Batch batch;
    private Image image = new Image(new Texture("assets/sprite/fireline.png"));
    private Image image1 = new Image(new Texture("assets/sprite/firewall.png"));
    private Image image2 = new Image(new Texture("assets/sprite/verticalfireline1.png"));
    private Image image3 = new Image(new Texture("assets/sprite/verticalfireline2.png"));
    private Label lab;

    private  Stage stage;
    private  Skin texture;

    private Music music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/a-cross-for-those-who-died.mp3"));
    OptionManager opt= new OptionManager();

    @Override
    public void show() {
        opt.loadData();

        music.setVolume(opt.getVm());
        music.setLooping(true);
        image.setVisible(true);
        image1.setVisible(false);
        image2.setVisible(false);
        image3.setVisible(false);

        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(image3);
        stage.addActor(image2);
        stage.addActor(image1);
        stage.addActor(image);
        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        lab = new Label("FIGHT TO THRIVE THIS \nWAR WITHOUT REASON",texture);
        lab.setFontScale(2);
        stage.addActor(lab);

        Actor fullScreenListener = new Actor();
        fullScreenListener.setBounds(0, 0, stage.getWidth(), stage.getHeight());

        fullScreenListener.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Schermo cliccato!");
                lab.setText("BECAUSE NOW DAWNS THIER RECKONING,\nAND EVEN THY GORE SHALL GLISTEN BEFORE THE CREATION OF MAN!");
                image1.setVisible(true);
            }
        });

        stage.addActor(fullScreenListener);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        //
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
        stage.dispose();
        music.dispose();
    }
}
