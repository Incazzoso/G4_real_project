package g4.group.allCardUtilities;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import g4.group.allCardUtilities.Unit;

public class CardActor extends Image {
    private Unit card;

    public CardActor(Unit card, DragAndDrop dragAndDrop) {
        super(card.getImage().getDrawable());
        this.card = card;

        // Aggiungi la funzionalità di drag and drop
        dragAndDrop.addSource(new DragAndDrop.Source(this) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(card);
                Image img = new Image(getDrawable());
                img.setSize(150,200);
                payload.setDragActor(img); // Visualizza l'anteprima mentre trascini
                return payload;
            }
        });
    }

    public Unit getCard() {
        return card;
    }
}
