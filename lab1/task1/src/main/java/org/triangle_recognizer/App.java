package org.triangle_recognizer;

import org.triangle_recognizer.pojo.Triangle;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Incorrect number of parameters");
        } else {
            Triangle triangle = new Triangle(
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));
            System.out.println(TriangleRecognizer.recognize(triangle).toString());
        }
    }
}
