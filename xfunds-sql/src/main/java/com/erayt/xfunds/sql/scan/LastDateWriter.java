package com.erayt.xfunds.sql.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public final class LastDateWriter {
    private static Logger logger = LoggerFactory.getLogger(LastDateWriter.class);

    public static void writer(String dbType) throws IOException {
        String userPath = System.getProperties().getProperty("user.home");
        File sqlDir = new File(userPath,".xfunds-sql");
        if(!sqlDir.exists()){
            Assert.state(sqlDir.mkdir(),"创建xfunds-sql个人存档目录失败.");
        }
        Properties properties = new Properties();
        properties.setProperty(dbType.concat(".lastDate"),lastDate());
        properties.store(Files.newBufferedWriter(Paths.get(userPath,".xfunds-sql","options.properties")), "修改最后执行日期");
        logger.info("成功将这次执行日期写入文件:options.properties,{}",properties);
    }

    private static String lastDate(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static Integer getLast(String dbType) throws IOException {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(Paths.get(System.getProperties().getProperty("user.home"),".xfunds-sql","options.properties")));

        String lastDate = properties.getProperty(dbType.concat(".lastDate"));

        return StringUtils.hasLength(lastDate) ? Integer.valueOf(lastDate) : null;
    }
}
