package triangle_recognizer;

import triangle_recognizer.entities.EuclidTriangle;
import triangle_recognizer.entities.Triangle;

import java.math.BigDecimal;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("ERROR");
        } else {
            try {
                Triangle triangle = new EuclidTriangle(
                        new BigDecimal(args[0]),
                        new BigDecimal(args[1]),
                        new BigDecimal(args[2]));
                System.out.println(triangle);
            } catch (Exception exception) {
                System.out.println("ERROR");
            }
        }
    }
}
