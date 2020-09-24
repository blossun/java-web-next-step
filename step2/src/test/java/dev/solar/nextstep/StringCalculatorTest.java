package dev.solar.nextstep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringCalculatorTest {
    private StringCalculator cal;

    @BeforeEach
    void setup() {
        cal = new StringCalculator();
    }

    @Test
    public void add_null_또는_빈문자() {
        assertThat(cal.add(null)).isZero();
        assertThat(cal.add("")).isZero();
    }

}
