package dev.solar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        String[] actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자열을 구분자로 분리2 - single 구분자")
    @Test
    void step1_2() {
        String str = "1,2,3";
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자열을 구분자로 분리3 - 커스텀 구분자 ;")
    @Test
    void step1_3() {
        String str = "//;\n1;2;3";
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자열을 구분자로 분리4 - 커스텀 구분자 *")
    @Test
    void step1_4() {
        String str = "//*\n1*2*3"; // *를 구분자로 쓰려면 split("\\*"); 로 써줘야함
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자열을 구분자로 분리4 - 커스텀 구분자 \n")
    @Test
    void step1_5() {
        String str = "//*\n1*2*3";
        String[] expected = {"1", "2", "3"};
        String[] actual = cal.splitString(str);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("문자배열을 숫자배열로 변환")
    @Test
    void step2() {
        String[] strings = {"1", "2", "3"};
        int[] expected = {1, 2, 3};
        assertThat(cal.stringsToIntegers(strings)).isEqualTo(expected);
    }

    @DisplayName("숫자리스트의 합")
    @Test
    void step3() {
        int[] integers = {1, 2, 3};
        int expected = 6;
        assertThat(cal.sumOfIntegers(integers)).isEqualTo(expected);
    }

    @DisplayName("문자열 계산 - 문자열이 빈 경우")
    @Test
    void calculate() {
        assertThat(cal.calculate("")).isZero();
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
        assertThat(cal.calculate("//*\n1*2*3")).isEqualTo(6);
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

    @DisplayName("음수값 입력에대한 exception 처리 2")
    @Test
    void 음수값에러2() {
        String str = "1,2,-3";

        // WHEN
        NotAllowedValueException thrown = assertThrows(NotAllowedValueException.class, () -> cal.calculate(str));
        // If the exception has not been thrown, the above test has failed.

        // THEN
        // And now you may futther inspect the returned exception ...
        assertThat(thrown).isInstanceOf(NotAllowedValueException.class)
                .hasMessageContaining("음수");
    }
}
