package Vegetables.Local;

import Vegetables.Interfaces.MiddlePrice;
import Vegetables.MyException;

public class Canned extends Seasonal implements MiddlePrice {

    int shelf_life;

    public Canned(String name, int calories, int price, String season, int shelf_life) throws MyException {
        super(name, calories, price, season);
        if (shelf_life <= 0)
            throw new MyException("THE DATA IS INCORRECT");
        this.shelf_life = shelf_life;
    }

    public Canned() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + shelf_life + " months ";
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public boolean equals(Object obj) {
        Canned obj1 = (Canned) obj;
        return super.equals(obj) && this.shelf_life == obj1.shelf_life;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + shelf_life;
    }

    @Override
    public void setSeason(String season) {
        super.setSeason(season);
    }

    public void setShelf_life(int shelf_life) {
        this.shelf_life = shelf_life;
    }

    @Override
    public String getSeason() {
        return super.getSeason();
    }

}
