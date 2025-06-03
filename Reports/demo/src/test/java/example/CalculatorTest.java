package example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calc = new Calculator();

    @Test void testAdd() {
        assertTrue(calc.add(2, 2) > 0);
    }

    @Test void testMultiply() {
        assertTrue(calc.multiply(20, 20) > 10);
    }

    @Test void testDivide() {
        assertEquals(2, calc.divide(4, 2));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calc.divide(1, 0));
        assertEquals("Division by zero", exception.getMessage());
    }
}
