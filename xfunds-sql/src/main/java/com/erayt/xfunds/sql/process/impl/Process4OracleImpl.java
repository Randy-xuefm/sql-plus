package com.erayt.xfunds.sql.process.impl;

import com.erayt.xfunds.sql.component.Schema;
import com.erayt.xfunds.sql.process.BatchProcess;
import com.erayt.xfunds.sql.scan.SqlFileScanner;
import com.erayt.xfunds.sql.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/7/6.
 * linux .sh文件生成
 */
public class Process4OracleImpl implements BatchProcess {

    private static Logger logger = LoggerFactory.getLogger(SqlFileScanner.class);

    @Override
    public void process(Schema schema, String path) throws IOException {
        Assert.state(StringUtils.hasLength(path),".sh文件生成路径为空.");
        if(schema == null || schema.isEmpty()){
            return;
        }
        path = PathUtils.cleanPath(path);
        for (Map.Entry<String, Collection<String>> entry : schema.entrySet()) {
            new Shell().create(path,entry.getKey(),entry.getValue());
            new Bat().create(path,entry.getKey(),entry.getValue());
        }

    }

    private boolean isWindows(){
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }



    static class Shell{

        public void create(String path,String shFileName,Collection<String> exeFileNames) throws IOException {
            File sh = createSHFile(path + File.separator + shFileName+".sh");
            File sql = createSHFile(path+ File.separator + shFileName + "_sh.sql");
            try( BufferedWriter writer = new BufferedWriter(new FileWriter(sh));
                 BufferedWriter sqlWriter = new BufferedWriter(new FileWriter(sql))
            ) {
                writeHead(writer,shFileName, path);
                writeBody(sqlWriter, exeFileNames);
            } catch (IOException e) {
                logger.info("生成.sh文件错误",e);
            }
        }

        private File createSHFile(String path) throws IOException {
            File sh = new File(path);
            if(sh.exists()){
                Assert.state(sh.delete(),"删除.sh文件失败,"+ sh.getAbsolutePath());
            }

            Assert.state(sh.createNewFile(),"创建.sh文件失败,"+ sh.getAbsolutePath());

            return sh;
        }

        private void writeHead(BufferedWriter writer,String schema, String path) throws IOException {
            writer.write("#!/bin/sh");
            writer.newLine();
            writer.write("base_path=\"" + path + "\"");
            writer.newLine();
            writer.write("echo start {} sql...".replace("{}",schema));
            writer.newLine();
            writer.write("read -p \"Enter server name(e.g.:127.0.0.1:1521/orcl):\" db_serv");
            writer.newLine();
            writer.write("read -p \"Enter database username(e.g.:fyxfunds):\" db_user");
            writer.newLine();
            writer.write("read -p \"Enter database password:\" db_pwd");
            writer.newLine();
            writer.write("echo 如执行结果出现乱码 请修改脚本中字符集编码 export nls_langzh对应值,默认UTF8");
            writer.newLine();
            writer.write("export nls_lang=AMERICAN_AMERICA.UTF8");
            writer.newLine();
            writer.write("echo please wait...");
            writer.newLine();
            writer.write("echo start execute base sql");
            writer.newLine();
            writer.write("cd ${base_path}");
            writer.newLine();
            writer.write("sqlplus $db_user/$db_pwd@//$db_serv @{sqlFileName}_sh.sql  >>log.log".replace("{sqlFileName}",schema));
            writer.newLine();
            writer.write("echo ::执行完成 日志请看脚本 log.log");
        }

        private void writeBody(BufferedWriter writer,Collection<String> fileNames) throws IOException {
            for (String fileName : fileNames) {
                writer.write("@@{fileName}".replace("{fileName}",fileName));
                writer.newLine();
            }
            writer.write("quit;");
        }
    }

    static class Bat{
        public void create(String path,String shFileName,Collection<String> exeFileNames) throws IOException {
            File sh = createSHFile(path + File.separator + shFileName+".bat");
            File sql = createSHFile(path+ File.separator + shFileName + "_bat.sql");
            try( BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sh), "GBK"));
                 BufferedWriter sqlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sql), "GBK"))
            ) {
                writeHead(writer,shFileName, path);
                writeBody(sqlWriter, exeFileNames);
            } catch (IOException e) {
                logger.info("生成.sh文件错误",e);
            }
        }

        private File createSHFile(String path) throws IOException {
            File sh = new File(path);
            if(sh.exists()){
                Assert.state(sh.delete(),"删除.sh文件失败,"+ sh.getAbsolutePath());
            }

            Assert.state(sh.createNewFile(),"创建.sh文件失败,"+ sh.getAbsolutePath());

            return sh;
        }

        private void writeHead(BufferedWriter writer,String schema, String path) throws IOException {
            writer.write("@echo off");
            writer.newLine();
            writer.write("@echo 注意schema的值,输入正确的用户名,密码");
            writer.newLine();
            writer.write("@echo ::schema [{}]".replace("{}",schema));
            writer.newLine();
            writer.write("@set DB_SERV=");
            writer.newLine();
            writer.write("@set /p DB_SERV=Enter server address(e.g.:127.0.0.1:1521/orcl): ");
            writer.newLine();
            writer.write("@set DB_USER=");
            writer.newLine();
            writer.write("@set /p DB_USER=Enter database username(e.g.:root)(xfunds): ");
            writer.newLine();
            writer.write("@set DB_PWD=");
            writer.newLine();
            writer.write("@set /p DB_PWD=Enter database password(xfunds): ");
            writer.newLine();
            writer.write("set nls_lang=AMERICAN_AMERICA.UTF8");
            writer.newLine();
            writer.write("echo start execute base sql");
            writer.newLine();
            writer.write("cd /d {}".replace("{}",path));
            writer.newLine();
            writer.write("sqlplus %DB_USER%/%DB_PWD%@//%DB_SERV% @{sqlFileName}_bat.sql  >>log.log".replace("{sqlFileName}",schema));
            writer.newLine();
            writer.write("@echo ::执行完成 日志请看脚本 log.log");
            writer.newLine();
            writer.write("pause");
            writer.newLine();
            writer.write(":end");
        }

        private void writeBody(BufferedWriter sqlWriter,Collection<String> fileNames) throws IOException {
            for (String fileName : fileNames) {
                sqlWriter.write("@@{fileName}".replace("{fileName}",fileName));
                sqlWriter.newLine();
            }
            sqlWriter.write("quit;");
        }
    }
}
