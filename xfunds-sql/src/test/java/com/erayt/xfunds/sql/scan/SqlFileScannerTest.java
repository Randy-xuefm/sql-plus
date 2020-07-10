package com.erayt.xfunds.sql.scan;

import com.erayt.xfunds.sql.config.PatchConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Created by fenming.xue on 2020/6/29.
 */
class SqlFileScannerTest {



    @Test
    void scan() throws IOException {
        PatchConfig config = new PatchConfig();
        config.setDbType("ORA");
        config.setScanPackage("E:\\WorkSpace\\xfunds_dev4.0");
        config.setTargetPackage("C:\\Users\\xue\\Desktop\\sql\\");
        config.setLastDate(20200601);
        SqlFileScanner scanner = new SqlFileScanner(config);

        scanner.scan();


    }
}