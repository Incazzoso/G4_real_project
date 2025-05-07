import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import g4.group.allCardUtilities.Hand;
import g4.group.allCardUtilities.MyProfile;
import g4.group.allCardUtilities.Player;

public class GameManager {

    private Player player1, player2, currentPlayer;
    private Stage stage;

    public GameManager(Stage stage) {
        this.stage = stage;
        player1 = new MyProfile().getMyself();
        player2 = new Player("Giocatore 2", new Hand());
        currentPlayer = player1; // Il primo turno parte con il Player 1
    }

    public void endTurn() {
        currentPlayer.startTurn(); // Aggiorna mana e carte
        currentPlayer = (currentPlayer == player1) ? player2 : player1; // Cambio turno
    }
}
