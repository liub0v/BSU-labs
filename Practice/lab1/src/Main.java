import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        try {
            Town town1 = new Town("town1");
            Town town2 = new Town("town2");
            Town town3 = new Town("town3");

            District district1 = new District("district1", town1, 12);
            District district2 = new District("district2", town2, 17);
            District district3 = new District("district3", town3, 34);
            ArrayList<District> districts1 = new ArrayList<District>();
            ArrayList<District> districts2 = new ArrayList<District>();
            districts1.add(district1);
            districts1.add(district2);
            districts2.add(district3);

            Region region1 = new Region("region1", town1, districts1);
            Region region2 = new Region("region2", town3, districts2);
            ArrayList<Region> regions1 = new ArrayList<Region>();
            regions1.add(region1);
            regions1.add(region2);

            State state1 = new State("State1", town3, regions1);
            ArrayList<State> states = new ArrayList<State>();
            states.add(state1);
            System.out.println("Capital:" + state1.getCapital());
            System.out.println("Regions:" + state1.numberOfRegions());
            System.out.println("Area:" + state1.area());
            state1.printRegionalCenters();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

    }
}
