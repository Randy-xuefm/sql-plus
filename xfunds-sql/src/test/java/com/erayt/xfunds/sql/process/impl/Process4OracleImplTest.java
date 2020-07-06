package com.erayt.xfunds.sql.process.impl;

import com.erayt.xfunds.sql.component.Schema;
import com.erayt.xfunds.sql.scan.SqlSchemaParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by fenming.xue on 2020/7/6.
 */
class Process4OracleImplTest {

    @Test
    void process() throws IOException {
        String path = "C:\\Users\\xue\\Desktop\\sql\\";

        Process4OracleImpl shProcess = new Process4OracleImpl();
        SqlSchemaParser parser = new SqlSchemaParser(Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).collect(Collectors.toList()));
        Schema schema = parser.parser();
        shProcess.process(schema, path);
    }
}