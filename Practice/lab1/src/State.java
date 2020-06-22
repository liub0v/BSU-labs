import java.util.ArrayList;

public class State {
    private String name;
    private Town capital;
    private ArrayList<Region> regions;


    State(String name, Town capital, ArrayList<Region> regions) throws MyException {
        if (name == null || capital == null || regions == null)
            throw new MyException("Incorrect data!");
        this.name = name;
        this.capital = capital;
        this.regions = regions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        State state = (State) obj;

        return name.equals(state.name) &&
                capital == state.capital;
    }

    @Override
    public int hashCode() {
        return (int) ((name == null) ? 0 : name.hashCode()) + capital.hashCode();
    }

    @Override
    public String toString() {
        return name + " Capital:" + capital;
    }


    Town getCapital() {
        return capital;
    }

    int area() {
        int area = 0;
        for (Region i : regions) {
            area += i.area();
        }
        return area;
    }

    void printRegionalCenters() {
        System.out.println("Regional centers:");
        for (Region i : regions) {
            System.out.println(i.getRegional_center());
        }
    }

    int numberOfRegions() {
        return regions.size();
    }

}
