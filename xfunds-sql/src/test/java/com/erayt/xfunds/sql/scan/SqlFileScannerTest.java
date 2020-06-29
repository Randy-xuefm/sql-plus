package com.erayt.xfunds.sql.scan;

import com.erayt.xfunds.sql.config.PatchConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fenming.xue on 2020/6/29.
 */
class SqlFileScannerTest {



    @Test
    void scan() {
        PatchConfig config = new PatchConfig();
        config.setDbType("ORA");
        config.setScanPackage("E:\\WorkSpace\\xfunds_dev4.0");
        config.setTargetPackage("C:\\Users\\xue\\Desktop\\sql\\");
        config.setLastDate(20200601);
        SqlFileScanner scanner = new SqlFileScanner(config);

        scanner.scan();


    }
}