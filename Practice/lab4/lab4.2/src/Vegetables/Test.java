package Vegetables;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        try {
            int counter = 1;
            Salad salad;
            Comparator<Vegetable> priceComparator = new PriceComparator();
            ReadXML xml_read = new ReadXML();
            salad = xml_read.readDataXML();
            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.println("Menu:\n1)Calories(a,b)\n2)Vegetable salad\n3)Price ascending\n4)Salad Calories\n5)Exit");
                System.out.print("Input a number: ");
                int num = in.nextInt();
                switch (num) {
                    case 1:
                        System.out.println("Enter a:");
                        int a = in.nextInt();
                        System.out.println("Enter b:");
                        int b = in.nextInt();

                        for (Vegetable i : salad) {
                            i.CalorieRange(a, b);
                        }

                        break;
                    case 2:
                        for (Vegetable i : salad) {
                            System.out.println(counter + ") " + i);
                            counter++;
                        }
                        break;
                    case 3:
                        salad.sort(priceComparator);
                        for (Vegetable i : salad) {
                            System.out.println(counter + ") " + i);
                            counter++;
                        }
                        break;
                    case 5:
                        return;
                    case 4:
                        System.out.println(salad.SaladCalories());
                        break;

                    default:
                        System.out.println("INCORRECT INPUT!");
                        break;
                }
            }
        } catch (MyException e) {
            System.out.println("INCORRECT INPUT");

        } catch (InputMismatchException e) {
            System.out.println("INCORRECT ENTRY");
        }
    }
}
