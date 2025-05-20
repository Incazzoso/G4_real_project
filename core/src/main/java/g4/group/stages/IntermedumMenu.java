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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import g4.group.allCardUtilities.OptionManager;
import g4.group.gameMechanics.GameScreen;

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
    private Image tex1 = new Image(new Texture("assets/sprite/TXT1.png"));
    private Image tex2 = new Image(new Texture("assets/sprite/TXT2.png"));
    private Image tex3 = new Image(new Texture("assets/sprite/TXT3.png"));
    private int cont=1;

    private  Stage stage;

    private Music music = Gdx.audio.newMusic(Gdx.files.internal("assets/music/sisiphian-task.mp3"));
    OptionManager opt= new OptionManager();

    @Override
    public void show() {
        opt.loadData();
        // Setup background music
        music.setVolume(opt.getVm());
        music.setLooping(true);
        music.play();


        music.setVolume(opt.getVm());
        music.setLooping(true);
        image.setVisible(true);
        image1.setVisible(false);
        image2.setVisible(false);
        image3.setVisible(false);
        tex1.setVisible(true);
        tex2.setVisible(false);
        tex3.setVisible(false);

        batch = new SpriteBatch();

        stage = new Stage(new FitViewport(1024, 980));
        Gdx.input.setInputProcessor(stage);
        image.setSize(1024,340);
        image.setPosition(0,-30);

        stage.addActor(image3);
        stage.addActor(image2);
        stage.addActor(image1);
        stage.addActor(image);
        stage.addActor(tex3);
        stage.addActor(tex2);
        tex1.setPosition(65,450);
        stage.addActor(tex1);

        Actor fullScreenListener = new Actor();
        fullScreenListener.setBounds(0, 0, stage.getWidth(), stage.getHeight());
        fullScreenListener.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Schermo cliccato!");
                if(getCont()==1){
                    image1.setSize(1024,image1.getHeight()+60);
                    image1.setVisible(true);
                    tex1.setVisible(false);
                    tex2.setPosition(35,450);
                    tex2.setVisible(true);
                    setCont(2);
                }else{
                    if(getCont()==2){
                        image2.setPosition(-70,240);
                        image3.setPosition(760,240);
                        image2.setVisible(true);
                        image3.setVisible(true);
                        tex2.setVisible(false);
                        tex3.setSize(952,137);
                        tex3.setPosition(35,450);
                        tex3.setVisible(true);
                        setCont(3);
                    }else{
                        System.out.println("Schermo cliccato!");
                        music.stop();
                        game.setScreen(new GameScreen());
                    }
                }
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

    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }
}
