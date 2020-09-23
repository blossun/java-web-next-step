package dev.solar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    private Calculator cal;

    @BeforeEach
    void setUp() {
        cal = new Calculator();
        System.out.println("before");
    }

    @Test
    void add() {
        assertEquals(9, cal.add(6, 3));
        System.out.println("add");
    }

    @Test
    void subtract() {
        assertEquals(3, cal.subtract(6, 3));
        System.out.println("subtract");
    }

    @Test
    void multiply() {
        assertEquals(18, cal.multiply(6, 3));
        System.out.println("multiply");
    }

    @Test
    void divide() {
        assertEquals(2, cal.divide(6, 3));
        System.out.println("divide");
    }

    @AfterEach
    void teardown() {
        System.out.println("teardown");
    }
}
