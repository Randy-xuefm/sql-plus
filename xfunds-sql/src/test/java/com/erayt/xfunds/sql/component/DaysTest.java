package com.erayt.xfunds.sql.component;

import org.junit.jupiter.api.Test;

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
}