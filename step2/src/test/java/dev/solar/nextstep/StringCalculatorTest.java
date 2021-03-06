package dev.solar.nextstep;

import dev.solar.NotAllowedValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringCalculatorTest {
    private StringCalculator cal;

    @BeforeEach
    void setup() {
        cal = new StringCalculator();
    }

    @Test
    void add_null_또는_빈문자() {
        assertThat(cal.add(null)).isZero();
        assertThat(cal.add("")).isZero();
    }

    @Test
    void add_숫자하나() {
        assertThat(cal.add("1")).isEqualTo(1);
    }

    @Test
    void add_쉼표구분자() {
        assertThat(cal.add("1,2")).isEqualTo(3);
    }

    @Test
    void add_쉽표_또는_콜론_구분자() {
        assertThat(cal.add("1,2:3")).isEqualTo(6);
    }

    @Test
    void add_custom_구분자() {
        assertThat(cal.add("//;\n1;2;3")).isEqualTo(6);
    }

//    @Test(expected = RuntimeException.class) //junit4 문법
    @Test
    void add_negative() {
        // WHEN
        Throwable thrown = catchThrowable(() -> cal.add("-1,2,3"));

        //THEN
        assertThat(thrown).isInstanceOf(RuntimeException.class);
    }
}
