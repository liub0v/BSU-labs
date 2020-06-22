public class Town {
    private String name;

    Town(String name) throws MyException {

        if (name == null)
            throw new MyException("Incorrect data!");
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Town town = (Town) obj;
        assert town != null;
        return name.equals(town.name);
    }

    public int hashCode() {
        return (int) ((name == null) ? 0 : name.hashCode());
    }

}
