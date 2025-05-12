package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import g4.group.allCardUtilities.OptionManager;

public class SettingMenu implements Screen {
    private Game game;

    public SettingMenu(Game game) {
        this.game = game;
    }

    private Batch batch;
    private Texture image;

    private  Stage stage;
    private  Skin texture;
    private  Table table;
    TextButton button,button1;

    //VOLUME of MUSIC & EFFECT
    private float volMusic;
    private Slider volMSlider;
    private Label volMname;

    private float volEffect;
    private  Slider volESlider;
    private Label volEname;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/a-link-to-the-earth.mp3"));
    private Music eff = Gdx.audio.newMusic(Gdx.files.internal("assets/music/effect/click-effect.mp3"));
    OptionManager opt= new OptionManager();

    @Override
    public void show() {
        opt.loadData();

        music.setVolume(opt.getVm());
        music.setLooping(true);
        music.play();

        batch = new SpriteBatch();
        image = new Texture("sprite/Menu_game_bg.png");

        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));

        //MUSIC
        volMname = new Label("Music Volume: " + opt.getVm(), texture);
        volMSlider = new Slider(0, 1, 0.05f, false, texture);
        volMSlider.setValue(opt.getVm());

        table.add(volMname).pad(10).row();
        table.add(volMSlider).pad(10).row();

        volMSlider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                volMusic = volMSlider.getValue();
                volMname.setText("Music Volume: " + volMusic);
            }
        });

        SelectBox<String> selectBox=new SelectBox<String>(texture);
        selectBox.setItems("in the name of those who fallen","grace for the lost","pinocchio's last lies","che sia vita o morte");
        table.add(selectBox).pad(10).row();

        //EFFECT
        volEname = new Label("Effect Volume: " + opt.getVe(), texture);
        volESlider = new Slider(0, 1, 0.05f, false, texture);
        volESlider.setValue(opt.getVe());

        table.add(volEname).pad(10).row();
        table.add(volESlider).pad(10).row();

        volESlider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                volEffect = volESlider.getValue();
                volEname.setText("Effect Volume: " + volEffect);
            }
        });

        //RETURN BUTTON
        button = new TextButton("reset", texture);
        button1 = new TextButton("Return", texture);

        table.add(button).row();
        table.add(button1).pad(10);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eff.play();
                eff.setVolume(opt.getVe());
                volMSlider.setValue(0.75f);
                volESlider.setValue(0.75f);
                volMname.setText("Music Volume: " + 0.75f);
                volEname.setText("Effect Volume: " + 0.75f);
            }
        });
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eff.play();
                eff.setVolume(opt.getVe());
                handleSelection();
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.draw(image, 0, 0);
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
        opt.saveOpt(volMSlider.getValue(),volESlider.getValue());
        music.dispose();
        game.setScreen(new StartMenu(game));
    }
}
