package org.triangle_recognizer;

import org.triangle_recognizer.pojo.Triangle;

public class TriangleRecognizer {
    public static TriangleRecognitionResult recognize(Triangle triangle) {
        if (isCorrectTriangle(triangle)) {
            if (isEquilateral(triangle)) {
                return TriangleRecognitionResult.Equilateral;
            } else if (isIsosceles(triangle)) {
                return TriangleRecognitionResult.Isosceles;
            } else {
                return TriangleRecognitionResult.Normal;
            }
        } else {
            return TriangleRecognitionResult.NotTriangle;
        }
    }

    private static boolean isCorrectTriangle(Triangle triangle) {
        return isAllSideLengthArePositive(triangle) &&
                triangle.getFirstSide() < (triangle.getSecondSide() + triangle.getThirdSide()) &&
                triangle.getSecondSide() < (triangle.getFirstSide() + triangle.getThirdSide()) &&
                triangle.getThirdSide() < (triangle.getSecondSide() + triangle.getFirstSide());
    }

    private static boolean isAllSideLengthArePositive(Triangle triangle) {
        return (triangle.getFirstSide() > 0) && (triangle.getSecondSide() > 0) && (triangle.getThirdSide() > 0);
    }

    private static boolean isEquilateral(Triangle triangle) {
        return (triangle.getFirstSide() == triangle.getSecondSide()) &&
                (triangle.getSecondSide() == triangle.getThirdSide());
    }

    private static boolean isIsosceles(Triangle triangle) {
        return (triangle.getFirstSide() == triangle.getSecondSide()) ||
                (triangle.getSecondSide() == triangle.getThirdSide()) ||
                (triangle.getThirdSide() == triangle.getFirstSide());
    }
}
