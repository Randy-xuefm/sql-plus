package com.person.xue.sql.scan;

import com.person.xue.sql.config.PatchConfig;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void findAllSqlFiles(){
        List<File> resultList = new ArrayList<>();

        resultList.addAll(Lists.newArrayList());

        assert resultList.size() == 0;
    }
}