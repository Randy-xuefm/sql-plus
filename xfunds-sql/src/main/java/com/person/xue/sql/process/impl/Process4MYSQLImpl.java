package com.person.xue.sql.process.impl;

import com.person.xue.sql.component.Schema;
import com.person.xue.sql.process.BatchProcess;
import com.person.xue.sql.scan.SqlFileScanner;
import com.person.xue.sql.utils.PathUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/7/6.
 * windows .bat文件生成.
 */
public class Process4MYSQLImpl implements BatchProcess {

    private static Logger logger = LoggerFactory.getLogger(SqlFileScanner.class);

    @Override
    public void process(Schema schema, String path) throws IOException {
        Assert.state(StringUtils.hasLength(path), ".sh文件生成路径为空.");
        if(schema == null || schema.isEmpty()){
            return;
        }
        path = PathUtils.cleanPath(path);

        Collection<String> fileNames = new ArrayList<>();
        for (Map.Entry<String, Collection<String>> entry : schema.entrySet()) {
            fileNames.addAll(entry.getValue());
        }

        new Shell().create(path, "xfunds", fileNames);
        new Bat().create(path, "xfunds", fileNames);
    }

    static class Shell{

        public void create(String path,String shFileName,Collection<String> exeFileNames) throws IOException {
            File sh = createSHFile(path + File.separator + shFileName+".sh");
            File sql = createSHFile(path+ File.separator + shFileName + "_sh.sql");
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(sh));
            BufferedWriter sqlWriter = new BufferedWriter(new FileWriter(sql))) {
                writeHead(writer,shFileName);
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

        private void writeHead(BufferedWriter writer, String schema) throws IOException {
            writer.write("#!/bin/sh");
            writer.newLine();
            writer.write("base_path=\"$PWD\"");
            writer.newLine();
            writer.write("echo start {} sql...".replace("{}",schema));
            writer.newLine();
            writer.write("read -p \"Enter server address(e.g.:127.0.0.1):\" db_serv");
            writer.newLine();
            writer.write("read -p \"Enter database name(e.g.:xfundsdb):\" db_name");
            writer.newLine();
            writer.write("read -p \"Enter database username(e.g.:xfunds):\" db_user");
            writer.newLine();
            writer.write("read -p \"Enter database password(e.g.:xfunds):\" db_pwd");
            writer.newLine();
            writer.write("echo 如执行结果出现乱码 请修改脚本中字符集编码 export nls_langzh对应值,默认UTF8");
            writer.newLine();
            writer.write("export LANG=\"zh_CN.UTF-8\"");
            writer.newLine();
            writer.write("echo please wait...");
            writer.newLine();
            writer.write("echo start execute base sql");
            writer.newLine();
            writer.write("cd ${base_path}");
            writer.newLine();
            writer.write("mysql -u $db_user -p$db_pwd -f -h $db_serv  $db_name --default-character-set=utf8 < {sqlFileName}_sh.sql>>log.log".replace("{sqlFileName}",schema));
            writer.newLine();
            writer.write("echo ::执行完成 日志请看脚本 log.log");
        }

        private void writeBody(BufferedWriter writer,Collection<String> fileNames) throws IOException {
            for (String fileName : fileNames) {
                writer.write("source {fileName}".replace("{fileName}",fileName));
                writer.newLine();
            }
        }
    }

    static class Bat{
        public void create(String path,String shFileName,Collection<String> exeFileNames) throws IOException {
            File sh = createSHFile(path + File.separator + shFileName+".bat");
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sh), StandardCharsets.UTF_8))) {
                writeHead(writer,shFileName, path);
                writeBody(writer, exeFileNames);
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
            writer.write("chcp 65001");
            writer.newLine();
            writer.write("@echo ::schema [{}]".replace("{}",schema));
            writer.newLine();
            writer.write("@set db_serv=");
            writer.newLine();
            writer.write("@set /p db_serv=Enter server address(e.g.:127.0.0.1):");
            writer.newLine();
            writer.write("@set db_name=");
            writer.newLine();
            writer.write("@set /p db_name=Enter database name(e.g.:xfundsdb):");
            writer.newLine();
            writer.write("@set db_user=");
            writer.newLine();
            writer.write("@set /p db_user=Enter database username(e.g.:root):");
            writer.newLine();
            writer.write("@set db_pwd=");
            writer.newLine();
            writer.write("@set /p db_pwd=Enter database password(e.g.:123456):");
            writer.newLine();
            writer.write("echo start execute base sql");
            writer.newLine();
            writer.write("cd /d {}".replace("{}",path));
            writer.newLine();

        }

        private void writeBody(BufferedWriter sqlWriter,Collection<String> fileNames) throws IOException {
            for (String fileName : fileNames) {
                sqlWriter.write("mysql -u %db_user% -p%db_pwd% -f -h %db_serv% %db_name% < {fileName}".replace("{fileName}",fileName));
                sqlWriter.newLine();
            }
            sqlWriter.write("@echo ::执行完成");
            sqlWriter.newLine();
            sqlWriter.write("pause");
            sqlWriter.newLine();
            sqlWriter.write(":end");
        }
    }
}
