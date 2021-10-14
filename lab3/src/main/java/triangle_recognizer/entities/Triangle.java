package triangle_recognizer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

public interface Triangle {
    BigDecimal getFirstSide();
    BigDecimal getSecondSide();
    BigDecimal getThirdSide();
    BigDecimal computeArea();
    BigDecimal computePerimeter();
    TriangleRecognitionResult getType();
}
