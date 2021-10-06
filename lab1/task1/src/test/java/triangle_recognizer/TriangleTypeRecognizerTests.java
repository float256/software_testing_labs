package triangle_recognizer;

import org.junit.Assert;
import org.junit.Test;
import triangle_recognizer.entities.Triangle;
import triangle_recognizer.entities.TriangleRecognitionResult;

import java.math.BigDecimal;

public class TriangleTypeRecognizerTests {
    @Test
    public void testNotATriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer
                .recognize(new Triangle(BigDecimal.valueOf(3), BigDecimal.valueOf(1), BigDecimal.valueOf(2)));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.NotTriangle);
    }

    @Test
    public void testIncorrectSide() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer
                .recognize(new Triangle(BigDecimal.valueOf(2), BigDecimal.valueOf(1), BigDecimal.valueOf(-1)));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.NotTriangle);
    }

    @Test
    public void testNormalTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(BigDecimal.valueOf(308), BigDecimal.valueOf(501), BigDecimal.valueOf(302)));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Normal);
    }

    @Test
    public void testIsoscelesTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(BigDecimal.valueOf(123), BigDecimal.valueOf(123), BigDecimal.valueOf(6)));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Isosceles);
    }

    @Test
    public void testEquilateralTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(BigDecimal.valueOf(1234), BigDecimal.valueOf(1234), BigDecimal.valueOf(1234)));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Equilateral);
    }
}
