package com.erayt.xfunds.sql.process.impl;

import com.erayt.xfunds.sql.utils.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by fenming.xue on 2020/7/6.
 */
class Process4OracleImplTest {

    @Test
    void process() throws IOException {
//        String path = "C:\\Users\\xue\\Desktop\\sql\\";
//
//        Process4OracleImpl shProcess = new Process4OracleImpl();
//        SqlSchemaParser parser = new SqlSchemaParser(Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).collect(Collectors.toList()));
//        Schema schema = parser.parser();
//        shProcess.process(schema, path);
    }

    @Test
    void cleanPath() {
        Process4OracleImpl process4Oracle = new Process4OracleImpl();
        String path = PathUtils.cleanPath("C:\\Users\\xue\\Desktop\\sql\\");

        assertThat(path).isEqualTo("C:\\Users\\xue\\Desktop\\sql");

        String path1 = PathUtils.cleanPath("/home/user");
        assertThat(path1).isEqualTo("/home/user");

        String path2 = PathUtils.cleanPath("C:\\Users\\xue\\Desktop\\sql");
        assertThat(path2).isEqualTo("C:\\Users\\xue\\Desktop\\sql");
    }
}