package triangle_recognizer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Triangle {
    private BigDecimal firstSide;
    private BigDecimal secondSide;
    private BigDecimal thirdSide;
}
