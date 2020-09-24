package dev.solar.nextstep;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SplitTest {

    @Test
    void split() {
        assertThat(new String[] {"1"}).isEqualTo("1".split(","));
        assertThat(new String[] {"1", "2"}).isEqualTo("1,2".split(","));
    }
}
