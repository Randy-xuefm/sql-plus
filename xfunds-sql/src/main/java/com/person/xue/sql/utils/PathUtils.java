package com.person.xue.sql.utils;

/**
 * Created by fenming.xue on 2020/7/7.
 */
public final class PathUtils {

    public static String cleanPath(String path){
        if(path.endsWith("/") || path.endsWith("\\")){
            return path.substring(0,path.length()-1);
        }
        return path;
    }
}
