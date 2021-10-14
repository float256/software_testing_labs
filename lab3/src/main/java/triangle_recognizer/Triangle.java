package triangle_recognizer;

import java.math.BigDecimal;

public interface Triangle {
    BigDecimal getFirstSide();
    BigDecimal getSecondSide();
    BigDecimal getThirdSide();
    BigDecimal computeArea();
    BigDecimal computePerimeter();
    TriangleRecognitionResult getType();
}
