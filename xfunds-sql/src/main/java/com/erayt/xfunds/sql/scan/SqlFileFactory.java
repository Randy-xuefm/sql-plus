package com.erayt.xfunds.sql.scan;

import com.erayt.xfunds.sql.component.DbType;
import com.erayt.xfunds.sql.component.Schema;
import com.erayt.xfunds.sql.config.PatchConfig;
import com.erayt.xfunds.sql.process.BatchProcess;
import com.erayt.xfunds.sql.process.impl.Process4MYSQLImpl;
import com.erayt.xfunds.sql.process.impl.Process4OracleImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/7/6.
 */
@Component
public class SqlFileFactory implements InitializingBean {

    private SqlFileScanner sqlFileScanner;

    private PatchConfig config;

    private Map<String, BatchProcess> processMap = new HashMap<>();

    public SqlFileFactory(PatchConfig config,SqlFileScanner sqlFileScanner){
        this.sqlFileScanner = sqlFileScanner;
        this.config = config;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        processMap.put("oracle",new Process4OracleImpl());
        processMap.put("mysql",new Process4MYSQLImpl());

        run();
    }

    public void run() throws IOException, URISyntaxException {
        List<File> sqlFileList = this.sqlFileScanner.scan();
        if(sqlFileList == null || sqlFileList.isEmpty()){
            return;
        }

        Schema schema = new SqlSchemaParser(sqlFileList).parser();

        BatchProcess process = this.processMap.get(DbType.get(this.config.getDbType()));

        Assert.state(process != null,"BatchProcess未找到具体的实现类,"+this.config.getDbType());
        process.process(schema,this.config.getTargetPackage());

        LastDateWriter.writer();
    }
}
