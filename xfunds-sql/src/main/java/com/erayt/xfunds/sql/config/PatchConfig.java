package com.erayt.xfunds.sql.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by fenming.xue on 2020/6/28.
 */
@ConfigurationProperties(prefix = "xfunds.sql.patch")
@Component
public class PatchConfig {
    /**
     * 扫描路径
     */
    private String scanPackage;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 复制到指定文件夹
     */
    private String targetPackage;

    /**
     *最后执行时间,方便增量拉取脚本
     */
    private Integer lastDate;

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public Integer getLastDate() {
        return lastDate;
    }

    public void setLastDate(Integer lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "PatchConfig{" +
                "scanPackage='" + scanPackage + '\'' +
                ", dbType='" + dbType + '\'' +
                ", targetPackage='" + targetPackage + '\'' +
                '}';
    }
}
