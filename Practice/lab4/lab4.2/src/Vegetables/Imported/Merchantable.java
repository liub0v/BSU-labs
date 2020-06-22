package Vegetables.Imported;
import Vegetables.Interfaces.*;
import Vegetables.MyException;

public class Merchantable extends Imported implements MiddlePrice,HighPrice {
    public Merchantable(String name, int calories, int price, String country) throws MyException {
        super(name, calories, price, country);
    }
    public Merchantable(){super();}

}
