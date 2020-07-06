package com.erayt.xfunds.sql.scan;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
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
    void writer() throws IOException, URISyntaxException {
        LastDateWriter writer = new LastDateWriter();
        writer.writer();

        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/application.properties"));
        assertThat(properties.getProperty("xfunds.sql.patch.lastDate")).isEqualTo("20200706") ;
    }

    @Test
    void lastDate(){
        String date = LocalDate.of(2020,7,6).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(date);
        assertThat(date).isEqualTo("20200706");
    }

    @Test
    void path() throws URISyntaxException {
        Path path = Paths.get(this.getClass().getClassLoader().getResource("application.properties").toURI());

        System.out.println(path.toString());
    }

    @Test
    void windows(){
        assert System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }
}