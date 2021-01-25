package com.person.xue.sql.component;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/6/28.
 */
public final class DbType {

    private final static Map<String,String> CACHE = new HashMap<>();

    static {
        CACHE.put("ORA","oracle");
        CACHE.put("MYSQL","mysql");
        CACHE.put("DB2","db2");
    }
    public static String get(String dbType){
        if(StringUtils.hasLength(dbType)){
            return CACHE.get(dbType);
        }

        return defaultDb();
    }

    public static String defaultDb(){
        return get("ORA");
    }
}
