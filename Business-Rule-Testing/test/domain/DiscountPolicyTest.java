
package test.before;

import domain.DiscountPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountPolicyTest {

    @Test
    void testDiscountCalculation() {
        double d1 = DiscountPolicy.calculateDiscount("VIP", 1500);
        double d2 = DiscountPolicy.calculateDiscount("VIP", 500);
        double d3 = DiscountPolicy.calculateDiscount("Regular", 600);
        double d4 = DiscountPolicy.calculateDiscount("Regular", 400);
        double d5 = DiscountPolicy.calculateDiscount("Guest", 1000);
        assertEquals(0.20, d1);
        assertEquals(0.0, d2);
        assertEquals(0.10, d3);
        assertEquals(0.0, d4);
        assertEquals(0.0, d5);
    }
}
