package g4.group;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    StartMenu startMenu;

    @Override
    public void create() {
        startMenu = new StartMenu();
        startMenu.show();
    }

    @Override
    public void render() {
        startMenu.render(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void dispose() {
        startMenu.dispose();
    }
}
