package dev.solar;

public class CalculatorTest {
    public static void main(String[] args) {
        Calculator cal = new Calculator();
        add(cal);
        subtract(cal);
        multiply(cal);
        divide(cal);
    }

    private static void divide(Calculator cal) {
        System.out.println(cal.divide(8,4));
    }

    private static void multiply(Calculator cal) {
        System.out.println(cal.multiply(2, 6));
    }

    private static void subtract(Calculator cal) {
        System.out.println(cal.subtract(5, 4));
    }

    private static void add(Calculator cal) {
        System.out.println(cal.add(3,4));
    }
}
