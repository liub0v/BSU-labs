package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Key {

    private StringProperty key;

    public Key(String key){
        this.key=new SimpleStringProperty(key);
    }

    public String getKey() {
        return key.get();
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public StringProperty keyProperty() {
        return key;
    }

    @Override
    public String toString() {
        return key.getValue().toString();
    }
}
