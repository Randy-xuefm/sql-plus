package com.erayt.xfunds.sql.scan;

import com.erayt.xfunds.sql.component.Schema;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fenming.xue on 2020/7/6.
 */
class SqlSchemaParserTest {

    @Test
    void parser() {
        String path = "C:\\Users\\xue\\Desktop\\sql\\";
        SqlSchemaParser parser = new SqlSchemaParser(Arrays.stream(new File(path).listFiles()).collect(Collectors.toList()));

        Schema schema = parser.parser();

        System.out.println(schema);
    }
}