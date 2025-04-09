package g4.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;

public class Main extends Game {
    StartMenu startMenu;

    @Override
    public void create() {
        startMenu = new StartMenu(this);
        setScreen(startMenu);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        startMenu.dispose();
    }
}
