package g4.group.gameMechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    private Batch batch;
    private Texture image;

    private  Stage stage;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("sprite/grey_soft_menu.png");

        stage = new Stage(new ScreenViewport()); // Usa un viewport migliore
        Gdx.input.setInputProcessor(stage); // Permette agli attori di ricevere input
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK); // Pulisce lo schermo

        batch.begin();
        batch.draw(image, 0, 0);
        batch.end();

        stage.act(delta); // Aggiorna animazioni
        stage.draw(); // Disegna gli attori
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
        Gdx.input.setInputProcessor(null); // Rimuovi input processor
        batch.dispose();
        image.dispose();
        stage.dispose();
    }
}
