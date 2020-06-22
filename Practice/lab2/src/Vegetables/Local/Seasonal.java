package Vegetables.Local;

import Vegetables.MyException;
import Vegetables.Vegetable;
import Vegetables.Interfaces.*;

public class Seasonal extends Vegetable implements LowPrice, Stock {

    String season;

    public Seasonal(String name, int calories, int price, String season) throws MyException {
        super(name, calories, price);
        if (season == null)
            throw new MyException("THE DATA IS INCORRECT");
        this.season = season;
    }

    public Seasonal() {
        super();
    }


    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString() + season + " season ";
    }

    @Override
    public boolean equals(Object obj) {
        Seasonal obj1 = (Seasonal) obj;
        return super.equals(obj) && this.season.equals(obj1.season);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + season.hashCode();
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }
}
