package Observer;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Key;

public class DisplayLogger implements Observer {


    private ListView<Key> keys;
    private Label textKey;
    private KeyLogger keyLogger;

    public DisplayLogger(KeyLogger keyLogger, ListView<Key> keys, Label textKey) {
        this.keys=keys;
        this.textKey=textKey;
        this.keyLogger=keyLogger;
        keyLogger.registerObserver(this);
    }

    @Override
    public void update(Key key) {
        this.keys.setItems(keyLogger.getLangs());
        textKey.setText(keyLogger.getKey().toString());

    }

}
