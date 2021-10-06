package triangle_recognizer;

import triangle_recognizer.entities.Triangle;
import triangle_recognizer.entities.TriangleRecognitionResult;

import java.math.BigDecimal;

public class TriangleRecognizer {
    public static TriangleRecognitionResult recognize(Triangle triangle) {
        if (isEquilateral(triangle)) {
            return TriangleRecognitionResult.Equilateral;
        } else if (isCorrectTriangle(triangle)) {
            if (isRightTriangle(triangle)) {
                return TriangleRecognitionResult.RightTriangle;
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
        BigDecimal first = triangle.getFirstSide();
        BigDecimal second = triangle.getSecondSide();
        BigDecimal third = triangle.getThirdSide();
        return isAllSideLengthArePositive(triangle) &&
                isLessThan(first, second.add(third)) &&
                isLessThan(second, third.add(first)) &&
                isLessThan(third, first.add(second));

    }

    private static boolean isAllSideLengthArePositive(Triangle triangle) {
        return isMoreThan(triangle.getFirstSide(), BigDecimal.ZERO) &&
                isMoreThan(triangle.getSecondSide(), BigDecimal.ZERO) &&
                isMoreThan(triangle.getThirdSide(), BigDecimal.ZERO);
    }

    private static boolean isEquilateral(Triangle triangle) {
        return triangle.getFirstSide().equals(triangle.getSecondSide()) &&
                triangle.getSecondSide().equals(triangle.getThirdSide()) &&
                isAllSideLengthArePositive(triangle);
    }

    private static boolean isIsosceles(Triangle triangle) {
        return triangle.getFirstSide().equals(triangle.getSecondSide()) ||
                triangle.getSecondSide().equals(triangle.getThirdSide()) ||
                triangle.getThirdSide().equals(triangle.getFirstSide());
    }

    private static boolean isRightTriangle(Triangle triangle) {
        BigDecimal firstPow2 = triangle.getFirstSide().pow(2);
        BigDecimal secondPow2 = triangle.getSecondSide().pow(2);
        BigDecimal thirdPow2 = triangle.getThirdSide().pow(2);

        return firstPow2.equals(secondPow2.add(thirdPow2)) ||
                secondPow2.equals(thirdPow2.add(firstPow2)) ||
                thirdPow2.equals(firstPow2.add(secondPow2));
    }

    private static boolean isLessThan(BigDecimal firstNum, BigDecimal secondNum) {
        return firstNum.compareTo(secondNum) < 0;
    }

    private static boolean isMoreThan(BigDecimal firstNum, BigDecimal secondNum) {
        return firstNum.compareTo(secondNum) > 0;
    }
}
