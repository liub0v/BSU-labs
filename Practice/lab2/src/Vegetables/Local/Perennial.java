package Vegetables.Local;
import Vegetables.MyException;
import  Vegetables.Vegetable;
import Vegetables.Interfaces.*;

import java.lang.reflect.MalformedParameterizedTypeException;

public class Perennial extends Vegetable implements MiddlePrice {

    public Perennial(String name,int calories, int price) throws MyException {
        super(name, calories, price);
    }
    public Perennial(){super();}
    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
