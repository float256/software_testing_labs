package triangle_recognizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

public class TestEuclidTriangle {
    @Test
    public void testRightTriangle() {
        Triangle triangle = new EuclidTriangle(
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(5)
        );

        Assertions.assertEquals(triangle.computeArea(), BigDecimal.valueOf(6));
        Assertions.assertEquals(triangle.computePerimeter(), BigDecimal.valueOf(12));
        Assertions.assertEquals(triangle.getType(), TriangleRecognitionResult.RightTriangle);
        Assertions.assertEquals(triangle.getFirstSide(), BigDecimal.valueOf(4));
        Assertions.assertEquals(triangle.getSecondSide(), BigDecimal.valueOf(3));
        Assertions.assertEquals(triangle.getThirdSide(), BigDecimal.valueOf(5));
    }

    @Test
    public void testIsoscelesTriangle() {
        Triangle triangle = new EuclidTriangle(
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(5)
        );

        Assertions.assertEquals(
                triangle.computeArea(),
                BigDecimal.valueOf(6.5 * 2.5 * 2.5 * 1.5).sqrt(new MathContext(2))
        );
        Assertions.assertEquals(triangle.computePerimeter(), BigDecimal.valueOf(13));
        Assertions.assertEquals(triangle.getType(), TriangleRecognitionResult.Isosceles);
        Assertions.assertEquals(triangle.getFirstSide(), BigDecimal.valueOf(4));
        Assertions.assertEquals(triangle.getSecondSide(), BigDecimal.valueOf(4));
        Assertions.assertEquals(triangle.getThirdSide(), BigDecimal.valueOf(5));
    }

    @Test
    public void testEquilateralTriangle() {
        Triangle triangle = new EuclidTriangle(
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10)
        );

        Assertions.assertEquals(
                triangle.computeArea(),
                BigDecimal.valueOf(15 * 5 * 5 * 5).sqrt(new MathContext(2))
        );
        Assertions.assertEquals(triangle.computePerimeter(), BigDecimal.valueOf(30));
        Assertions.assertEquals(triangle.getType(), TriangleRecognitionResult.Equilateral);
        Assertions.assertEquals(triangle.getFirstSide(), BigDecimal.valueOf(10));
        Assertions.assertEquals(triangle.getSecondSide(), BigDecimal.valueOf(10));
        Assertions.assertEquals(triangle.getThirdSide(), BigDecimal.valueOf(10));
    }

    @Test
    public void testNormalTriangle() {
        Triangle triangle = new EuclidTriangle(
                BigDecimal.valueOf(12.2),
                BigDecimal.valueOf(13.3),
                BigDecimal.valueOf(14.425)
        );

        Assertions.assertEquals(
                triangle.computeArea(),
                BigDecimal.valueOf(19.9625 * 7.7625 * 6.6625 * 5.5375).sqrt(new MathContext(2))
        );
        Assertions.assertEquals(triangle.computePerimeter(), BigDecimal.valueOf(39.925));
        Assertions.assertEquals(triangle.getType(), TriangleRecognitionResult.Normal);
        Assertions.assertEquals(triangle.getFirstSide(), BigDecimal.valueOf(12.2));
        Assertions.assertEquals(triangle.getSecondSide(), BigDecimal.valueOf(13.3));
        Assertions.assertEquals(triangle.getThirdSide(), BigDecimal.valueOf(14.425));
    }

    @Test
    public void testIncorrectTriangleCases() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2)
        ));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(2)
        ));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(50)
        ));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(-50),
                BigDecimal.valueOf(1.2),
                BigDecimal.valueOf(2.3)
        ));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(1.2),
                BigDecimal.valueOf(-50),
                BigDecimal.valueOf(2.3)
        ));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new EuclidTriangle(
                BigDecimal.valueOf(1.2),
                BigDecimal.valueOf(2.3),
                BigDecimal.valueOf(-50)
        ));
    }
}
