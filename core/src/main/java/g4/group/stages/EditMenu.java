package g4.group.stages;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class EditMenu implements Screen {

    private Game game;

    public EditMenu(Game game) {
        this.game = game;
    }

    private Batch batch;
    private Texture image;

    private  Stage stage;
    private  Skin texture;
    private  Table table;
    TextButton button;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("sprite/Menu_game_bg.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        texture = new Skin(Gdx.files.internal("assets/MenuButtonsTexture/DefaultGDX/uiskin.json"));
        button = new TextButton("Return", texture);
        table.add(button);

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
        batch.draw(image, 0, 0);
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
