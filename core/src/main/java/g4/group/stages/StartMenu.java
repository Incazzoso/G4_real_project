
package g4.group.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

//MENU PRINCIPALE IMPLEMENTS
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class StartMenu implements Screen {
    private SpriteBatch batch;
    private Texture image;
    //private Music mainmus;

    //MENU PRINCIPALE
    private Stage stage;
    private Table table;

    private TextButton[] buttons;

    private int selectIndex = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("menu_game_bg.png");
        //Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/mus1.mp3"));
        //mainMusic.play();
        //mainMusic.setVolume(0.75f);
        //mainMusic.setLooping(true);

        //INIZIO SCHERMATA D' INIZIO
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //TODO: sistemare la texture prima di avviare
        Skin texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        buttons = new TextButton[4];

        buttons[0] = new TextButton("Play", texture);
        buttons[1] = new TextButton("Edit Deck", texture);
        buttons[2] = new TextButton("Options", texture);
        buttons[3] = new TextButton("Exit", texture);

        for (int i = 0; i < buttons.length; i++) {
            table.add(buttons[i]).pad(10).row();
            final int index = i;

            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleSelection(index);
                }
            });
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(image, 0, 0);
        batch.end();

        Gdx.gl.glClearColor(0,0,0,1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        //mainMusic.stop();
        //mainMusic.dispose();

        stage.dispose();
    }

    //FUNZIONI MENU PRINCIPALE
    public void handleSelection(int index){
        selectIndex = index;
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setScale(i == selectIndex ? 1.2f : 1.0f);
        }
    }
}

