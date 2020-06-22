package com.kurets.address.util;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadObjectsHelper {

    // Read a ListProperty from ObjectInputStream (and return it)
    public static ListProperty<String> readListProp(ObjectInputStream s) throws IOException {
        ListProperty<String> lst = new SimpleListProperty<>(FXCollections.observableArrayList());
        int loop = s.readInt();
        for (int i = 0; i < loop; i++) {
            lst.add(s.readUTF());
        }

        return lst;
    }

    // automatic fill a set of properties with values contained in ObjectInputStream
    public static void readAllProp(ObjectInputStream s, Property... properties) throws IOException, ClassNotFoundException {
        for (Property prop : properties) {
            if (prop instanceof IntegerProperty) prop.setValue(s.readInt());
            else if (prop instanceof LongProperty) prop.setValue(s.readLong());
            else if (prop instanceof StringProperty) prop.setValue(s.readUTF());
            else if (prop instanceof BooleanProperty) prop.setValue(s.readBoolean());
            else if (prop instanceof ListProperty) prop.setValue(readListProp(s));
            else if (prop instanceof ObjectProperty) prop.setValue(s.readObject());
            else throw new RuntimeException(prop.toString());
        }
    }
}
