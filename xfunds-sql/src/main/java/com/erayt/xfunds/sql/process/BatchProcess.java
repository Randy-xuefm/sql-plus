package com.erayt.xfunds.sql.process;

import com.erayt.xfunds.sql.component.Schema;

import java.io.IOException;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public interface BatchProcess {

    /**
     * 生成.bat,.sh文件
     * @param schema 需要执行的sql脚本
     * @param path .sh生成路径
     */
    void process(Schema schema, String path) throws IOException;
}
