package Vegetables;

import java.util.Comparator;

public class PriceComparator implements Comparator<Vegetable> {
    @Override
    public int compare(Vegetable v1, Vegetable v2) {
        return v1.getPrice()-v2.getPrice();
    }


}
