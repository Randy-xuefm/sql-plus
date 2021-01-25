package com.person.xue.sql.scan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fenming.xue on 2020/7/6.
 */
class LastDateWriterTest {

    @Test
    void writer() throws IOException {
        LastDateWriter.writer("ORA");


        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Paths.get(System.getProperties().getProperty("user.home"),".xfunds-sql","options.properties")));
        assertThat(properties.getProperty("ORA.lastDate")).isEqualTo("20200711") ;
    }

    @Test
    void getLast() throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Paths.get(System.getProperties().getProperty("user.home"),".xfunds-sql","options.properties")));
        assertThat(properties.getProperty("ORA.lastDate")).isEqualTo("20200711") ;
    }

    @Test
    void lastDate(){
        String date = LocalDate.of(2020,7,6).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(date);
        assertThat(date).isEqualTo("20200706");
    }

    @Test
    void windows(){
        assert System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    @Test
    void userHome(){
        String path = System.getProperties().getProperty("user.home");
        System.out.println(path);
    }
}