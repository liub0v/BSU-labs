package Vegetables.Imported;

import Vegetables.MyException;
import Vegetables.Vegetable;

public class Imported extends Vegetable {

    private String country;

    Imported(String name, int calories, int price, String country) throws MyException {

        super(name, calories, price);
        if(country==null)
            throw new MyException("THE DATA IS INCORRECT");
        this.country=country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    Imported(){super();}

    @Override
    public void print() {
        System.out.println(this);

    }

    @Override
    public String toString() {
        return super.toString()+"From: "+country;
    }

    @Override
    public boolean equals(Object obj) {
        Imported obj1=(Imported) obj;
        return super.equals(obj) && this.country.equals(obj1.country);
    }

    @Override
    public int hashCode() {
        return super.hashCode()+country.hashCode();
    }

}
