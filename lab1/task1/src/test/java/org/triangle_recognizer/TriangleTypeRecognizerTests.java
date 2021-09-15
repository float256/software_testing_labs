package org.triangle_recognizer;

import org.junit.Assert;
import org.junit.Test;
import org.triangle_recognizer.pojo.Triangle;

public class TriangleTypeRecognizerTests {
    @Test
    public void testNotATriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer
                .recognize(new Triangle(3, 1, 2));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.NotTriangle);
    }

    @Test
    public void testIncorrectSide() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer
                .recognize(new Triangle(2, 1, -1));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.NotTriangle);
    }

    @Test
    public void testNormalTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(308, 501, 302));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Normal);
    }

    @Test
    public void testIsoscelesTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(123, 123, 6));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Isosceles);
    }

    @Test
    public void testEquilateralTriangle() {
        TriangleRecognitionResult recognitionResult = TriangleRecognizer.recognize(
                new Triangle(1234, 1234, 1234));
        Assert.assertEquals(recognitionResult, TriangleRecognitionResult.Equilateral);
    }
}
