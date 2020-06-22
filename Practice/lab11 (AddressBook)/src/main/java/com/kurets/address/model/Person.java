package com.kurets.address.model;


import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;
    private String regex = ";";

    @Override
    public String toString() {

        return getFirstName() + regex + getLastName() + regex + getStreet() + regex + getCity() + regex + getPostalCode() + regex + getBirthdayStr();
    }


    public Person() {
        this(null, null);
    }

    public Person(String object) {

        String[] data = object.split(regex);
        this.firstName = new SimpleStringProperty(data[0]);
        this.lastName = new SimpleStringProperty(data[1]);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty(data[2]);
        this.city = new SimpleStringProperty(data[3]);
        this.postalCode = new SimpleIntegerProperty(Integer.parseInt(data[4]));
        this.birthday = new SimpleObjectProperty<>(LocalDate.parse(data[5]));

    }

    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleObjectProperty<>(LocalDate.of(1999, 2, 21));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }


    public LocalDate getBirthday() {
        return birthday.get();
    }

    public String getBirthdayStr() {
        return birthday.get().toString();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

}