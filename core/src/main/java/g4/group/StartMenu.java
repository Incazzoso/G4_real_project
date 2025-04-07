
package g4.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

public class StartMenu implements Screen {
    private SpriteBatch batch;
    private Texture image;
    private Music mainmus;

    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("menu_game_bg.png");
        Music mainmus = Gdx.audio.newMusic(Gdx.files.internal("assets/music/mus1.mp3"));
        mainmus.play();
        mainmus.setVolume(0.75f);
        mainmus.setLooping(true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(image, 0, 0);
        batch.end();
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
        mainmus.stop();
        mainmus.dispose();
    }
}

