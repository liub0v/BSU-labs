package Vegetables.Imported;

import Vegetables.Interfaces.*;
import Vegetables.MyException;

public class Exotic extends Imported implements HighPrice,Vip {


    public Exotic(String name, int calories, int price, String country) throws MyException {
        super(name, calories, price, country);
    }
    public Exotic(){super();}

    @Override
    public void setCountry(String country) {
        super.setCountry(country);
    }
}
