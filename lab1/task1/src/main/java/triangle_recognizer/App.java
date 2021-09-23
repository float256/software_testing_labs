package triangle_recognizer;

import triangle_recognizer.pojo.Triangle;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("ERROR");
        } else {
            try {
                Triangle triangle = new Triangle(
                        Integer.parseInt(args[0]),
                        Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]));
                System.out.println(TriangleRecognizer.recognize(triangle).toString());
            } catch (Exception exception) {
                System.out.println("ERROR");
            }
        }
    }
}
