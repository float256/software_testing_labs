package triangle_recognizer;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Getter
public class EuclidTriangle implements Triangle {
    private final BigDecimal firstSide;
    private final BigDecimal secondSide;
    private final BigDecimal thirdSide;

    public EuclidTriangle(BigDecimal firstSide, BigDecimal secondSide, BigDecimal thirdSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;

        if (getType() == TriangleRecognitionResult.NotTriangle) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public BigDecimal computeArea() {
        BigDecimal semiPerimeter = firstSide.add(secondSide).add(thirdSide)
                .divide(BigDecimal.valueOf(2));
        BigDecimal semiPerimeterSubtractFirstSide = semiPerimeter.subtract(firstSide);
        BigDecimal semiPerimeterSubtractSecondSide = semiPerimeter.subtract(secondSide);
        BigDecimal semiPerimeterSubtractThirdSide = semiPerimeter.subtract(thirdSide);
        return semiPerimeter.multiply(semiPerimeterSubtractFirstSide)
                .multiply(semiPerimeterSubtractSecondSide)
                .multiply(semiPerimeterSubtractThirdSide)
                .sqrt(new MathContext(2));
    }

    @Override
    public BigDecimal computePerimeter() {
        return firstSide.add(secondSide).add(thirdSide);
    }

    @Override
    public TriangleRecognitionResult getType() {
        if (isEquilateral()) {
            return TriangleRecognitionResult.Equilateral;
        } else if (isCorrectTriangle()) {
            if (isRightTriangle()) {
                return TriangleRecognitionResult.RightTriangle;
            } else if (isIsosceles()) {
                return TriangleRecognitionResult.Isosceles;
            } else {
                return TriangleRecognitionResult.Normal;
            }
        } else {
            return TriangleRecognitionResult.NotTriangle;
        }
    }

    private boolean isCorrectTriangle() {
        return isAllSideLengthArePositive() &&
                isLessThan(firstSide, secondSide.add(thirdSide)) &&
                isLessThan(secondSide, thirdSide.add(firstSide)) &&
                isLessThan(thirdSide, firstSide.add(secondSide));

    }

    private boolean isAllSideLengthArePositive() {
        return isMoreThan(firstSide, BigDecimal.ZERO) &&
                isMoreThan(secondSide, BigDecimal.ZERO) &&
                isMoreThan(thirdSide, BigDecimal.ZERO);
    }

    private boolean isEquilateral() {
        return firstSide.equals(secondSide) &&
                secondSide.equals(thirdSide) &&
                isAllSideLengthArePositive();
    }

    private boolean isIsosceles() {
        return firstSide.equals(secondSide) ||
                secondSide.equals(thirdSide) ||
                thirdSide.equals(firstSide);
    }

    private boolean isRightTriangle() {
        BigDecimal firstSidePow2 = firstSide.pow(2);
        BigDecimal secondSidePow2 = secondSide.pow(2);
        BigDecimal thirdSidePow2 = thirdSide.pow(2);

        return firstSidePow2.equals(secondSidePow2.add(thirdSidePow2)) ||
                secondSidePow2.equals(thirdSidePow2.add(firstSidePow2)) ||
                thirdSidePow2.equals(firstSidePow2.add(secondSidePow2));
    }

    private static boolean isLessThan(BigDecimal firstSideNum, BigDecimal secondSideNum) {
        return firstSideNum.compareTo(secondSideNum) < 0;
    }

    private static boolean isMoreThan(BigDecimal firstSideNum, BigDecimal secondSideNum) {
        return firstSideNum.compareTo(secondSideNum) > 0;
    }
}
