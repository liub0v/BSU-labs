public class District {
    private String name;
    private Town town;
    private int area;

    District(String name, Town town, int area) throws MyException {
        if (name == null || town == null || area <= 0)
            throw new MyException("Incorrect data!");
        this.name = name;
        this.town = town;
        this.area = area;
    }

    @Override
    public String toString() {
        return name + " Town: " + town + " Area: " + area;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        District district = (District) obj;
        return name.equals(district.name);
    }

    public int hashCode() {
        return (int) ((name == null) ? 0 : name.hashCode());
    }

    int getArea() {
        return area;
    }
}
