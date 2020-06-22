package Vegetables;


import java.util.ArrayList;

public class Salad extends ArrayList<Vegetable> {
    public Salad(ArrayList<Vegetable> salad){
    }
    Salad(){}

    int SaladCalories(){
        int sum=0;
        for(Vegetable i: this){
            sum+=i.getCalories();
        }
        return sum;
    }



}
