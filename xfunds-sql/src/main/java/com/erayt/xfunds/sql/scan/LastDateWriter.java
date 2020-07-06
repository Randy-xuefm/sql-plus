package com.erayt.xfunds.sql.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public final class LastDateWriter {
    private static Logger logger = LoggerFactory.getLogger(LastDateWriter.class);

    public static void writer() throws IOException, URISyntaxException {
        Properties properties = new Properties();

        InputStream inputStream =LastDateWriter.class.getResourceAsStream("/application.properties");
        properties.load(inputStream);

        properties.setProperty("xfunds.sql.patch.lastDate", lastDate());
        properties.store(Files.newBufferedWriter(Paths.get(Objects.requireNonNull(LastDateWriter.class.getClassLoader().getResource("application.properties")).toURI())), "修改最后执行日期");

        logger.info("成功将这次执行日期写入文件:application.properties,{}",properties);
    }

    private static String lastDate(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
