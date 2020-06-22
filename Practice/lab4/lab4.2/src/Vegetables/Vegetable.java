package Vegetables;

abstract public class Vegetable {

    private String name;
    private int calories;
    private int price;

    public Vegetable() {
    }

    void CalorieRange(int a, int b) throws MyException {
        if (a < 0 || b < 0)
            throw new MyException("INCORRECT DATA!");
        if (a <= this.getCalories() && this.getCalories() <= b) {
            this.print();
        }

    }

    public Vegetable(String name, int calories, int price) throws MyException {

        if (name == null || calories <= 0 || price < 0)
            throw new MyException("THE DATA IS INCORRECT");
        this.name = name;
        this.calories = calories;
        this.price = price;
    }

    public abstract void print();


    @Override
    public String toString() {
        return new String(this.getClass().getName() + "\n |" + getName() + "| " +
                getCalories() + " kcal " + getPrice() + " RUB ");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Vegetable obj1 = (Vegetable) obj;
        return calories == obj1.calories &&
                price == obj1.price &&
                obj1.name.equals(name);
    }

    @Override
    public int hashCode() {
        return calories + price;
    }

    int getCalories() {
        return calories;
    }

    int getPrice() {
        return price;
    }

    private String getName() {
        return name;
    }

}


