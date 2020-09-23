package dev.solar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CalculatorTest {
    private Calculator cal;

    @BeforeEach
    void setUp() {
        cal = new Calculator();
    }

    @DisplayName("문자열을 구분자로 분리 - multi 구분자")
    @Test
    void step1() {
        String str = "1,2:3";
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str, null);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자열을 구분자로 분리2 - single 구분자")
    @Test
    void step1_2() {
        String str = "1,2,3";
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str, null);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자배열을 숫자배열로 변환")
    @Test
    void step2() {
        String[] strings = {"1", "2", "3"};
        Integer[] expected = {1, 2, 3};
        assertThat(cal.stringsToIntegers(strings)).isEqualTo(expected);
    }

    @DisplayName("숫자리스트의 합")
    @Test
    void step3() {
        Integer[] integers = {1, 2, 3};
        int expected = 6;
        assertThat(cal.sumOfIntegers(integers)).isEqualTo(expected);
    }

    @DisplayName("문자열 계산 - 문자열이 빈 경우")
    @Test
    void calculate() {
        int expected = 0;
        assertThat(cal.calculate("")).isEqualTo(expected);
    }

    @DisplayName("문자열 계산 2 - 1,2")
    @Test
    void calculate2() {
        int expected = 3;
        assertThat(cal.calculate("1,2")).isEqualTo(expected);
    }

    @DisplayName("문자열 계산 3 - 1,2:3")
    @Test
    void calculate3() {
        int expected = 6;
        assertThat(cal.calculate("1,2:3")).isEqualTo(expected);
    }

    @DisplayName("문자열 계산 4 - 4")
    @Test
    void calculate4() {
        assertThat(cal.calculate("4")).isEqualTo(4);
    }

    @DisplayName("문자열 계산 5 - 커스텀 구분자")
    @Test
    void calculate5() {
        assertThat(cal.calculate("//;\n1;2;3")).isEqualTo(6);
    }

    @DisplayName("문자열 계산 5 - 커스텀 구분자2")
    @Test
    void calculate6() {
        assertThat(cal.calculate("//*\n1*2*-3")).isEqualTo(6);
    }

    @DisplayName("구분자 추출 - 있는 경우")
    @Test
    void extractDelimiter() {
        String str = "//;\n";
        assertThat(cal.extractDelimiter(str)).isEqualTo(";");
    }

    @DisplayName("구분자 추출2")
    @Test
    void extractDelimiter1_2() {
        String str = "//*\n1*2*3";
        assertThat(cal.extractDelimiter(str)).isEqualTo(";");
    }

    @DisplayName("구분자 추출 - 없는 경우")
    @Test
    void extractDelimiter2() {
        String str = "1;2;3";
        assertThat(cal.extractDelimiter(str)).isNull();
    }

    @DisplayName("구분자 추출 - 빈 문자경우")
    @Test
    void extractDelimiter3() {
        String str = "//\n";
        assertThat(cal.extractDelimiter(str)).isEqualTo("");
    }

    @DisplayName("음수값 입력에대한 exception 처리")
    @Test
    void 음수값에러() {
        String str = "1,2,-3";
//        String str = "//*\n1*2*-3";

        // WHEN
        Throwable thrown = catchThrowable(() -> cal.calculate(str));

        //THEN
        assertThat(thrown).isInstanceOf(NotAllowedValueException.class)
                .hasMessageContaining("음수");
    }
}
