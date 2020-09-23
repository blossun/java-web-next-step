package dev.solar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {
    private Calculator cal;

    @BeforeEach
    void setUp() {
        cal = new Calculator();
    }

    @DisplayName("문자열을 구분자로 분리")
    @Test
    void step1() {
        String str = "1,2:3";
        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        List<String> actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자리스트를 숫자리스트로 변환")
    @Test
    void step2() {
        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("3");
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertThat(cal.stringsToIntegers(stringList)).isEqualTo(expected);
    }

    @DisplayName("숫자리스트의 합")
    @Test
    void step3() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        int expected = 6;
        assertThat(cal.sum(integerList)).isEqualTo(expected);
    }
}
