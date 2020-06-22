import java.util.ArrayList;

public class Region {
    private String name;
    private int area;
    private Town regional_center;
    private ArrayList<District> districts;

    Region(String name, Town regional_center, ArrayList<District> districts) throws MyException {
        if (name == null || regional_center == null || districts == null)
            throw new MyException("Incorrect data!");
        this.name = name;
        this.regional_center = regional_center;
        this.districts = districts;
    }

    @Override
    public String toString() {
        return name + " RC: " + regional_center + " Districts: " + districts;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Region region = (Region) obj;
        return name.equals(region.name) &&
                area == region.area &&
                regional_center == region.regional_center;
    }

    @Override
    public int hashCode() {
        return (int) (area + ((regional_center == null) ? 0 : name.hashCode()) +
                ((name == null) ? 0 : name.hashCode()));
    }


    int area() {
        for (District i : districts) {
            area += i.getArea();
        }
        return area;
    }

    Town getRegional_center() {
        return regional_center;
    }
}
