import lombok.EqualsAndHashCode;
import lombok.ToString;

import static org.testng.Assert.assertTrue;

@EqualsAndHashCode
@ToString
public class ProductFieldValidator {
    public static void validate(Product[] products) {
        for (Product product : products) {
            validate(product);
        }
    }

    public static void validate(Product product) {
        assertTrue(isCategoryIdValid(product),
                String.format("Invalid categoryId=%s. It must be in range 1 to 15", product.getCategoryId()));
        assertTrue(isStatusValid(product),
                String.format("Invalid status=%s. It must be in range 0 to 1", product.getStatus()));
        assertTrue(isHitValid(product),
                String.format("Invalid hit=%s. It must be in range 0 to 2", product.getHit()));
    }

    private static boolean isCategoryIdValid(Product product) {
        return (product.getCategoryId() >= 1) && (product.getCategoryId() <= 15);
    }

    private static boolean isStatusValid(Product product) {
        return (product.getStatus() == 0) || (product.getStatus() == 1);
    }

    private static boolean isHitValid(Product product) {
        return (product.getHit() >= 0) && (product.getHit() <= 2);
    }
}
