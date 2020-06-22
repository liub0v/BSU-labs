package Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Key;

import java.util.LinkedList;
import java.util.List;

public class KeyLogger implements Observable {

    private List<Observer> observerList;
    private Key key;
    private ObservableList<Key> langs = FXCollections.observableArrayList();

    ObservableList<Key> getLangs() {
        return langs;
    }

    Key getKey() {
        return key;
    }

    public KeyLogger(){
        observerList=new LinkedList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer: observerList){
            observer.update(key);
        }

    }

    public void setDate(Key key) {
        this.key = key;
        langs.add(key);
        notifyObservers();
    }
}
