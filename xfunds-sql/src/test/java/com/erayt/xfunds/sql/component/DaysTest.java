package com.erayt.xfunds.sql.component;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fenming.xue on 2020/6/28.
 */
class DaysTest {

    @Test
    public void year(){
        int date = 20190820;
        Days days = new Days(date);

        assertThat(days.getYear()).isEqualTo(2019);
        assertThat(days.getMonth()).isEqualTo(8);
        assertThat(days.getDay()).isEqualTo(20);

        assertThat(days.getYearAndMonth()).isEqualTo("201908");
    }

    @Test
    public void compare(){
        assertThat(LocalDate.of(2019, 1, 2).compareTo(LocalDate.of(2019, 2, 3))).isEqualTo(-1);
        assertThat(LocalDate.of(2019, 3, 2).compareTo(LocalDate.of(2019, 2, 3))).isEqualTo(1);
        assertThat(LocalDate.of(2019, 1, 2).compareTo(LocalDate.of(2019, 1, 2))).isEqualTo(0);
    }

    @Test
    public void match(){
        Days days = new Days(20190102);

        assertThat(days.match("patch_updateData_swap_20200619_02.sql")).isTrue();
        assertThat(days.match("patch_updateData_swap_20190102_02.sql")).isTrue();
    }

    @Test
    void matchDir() {
        Days days = new Days(20190102);

        assertThat(days.matchDir("201901")).isTrue();
        assertThat(days.matchDir("202001")).isTrue();
        assertThat(days.matchDir("201902")).isTrue();
    }

    @Test
    public void subString(){
        String s = "201901";
        assertThat(s.substring(0,4)).isEqualTo("2019");
        assertThat(s.substring(4,6)).isEqualTo("01");
    }
}