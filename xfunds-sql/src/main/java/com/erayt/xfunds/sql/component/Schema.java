package com.erayt.xfunds.sql.component;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public class Schema extends HashMap<String,Collection<String>> {

    private List<String> schemaList = new ArrayList<>();

    public Schema(String schema){
        this.schemaList.addAll(Arrays.stream(schema.split(",")).collect(Collectors.toList()));
    }

    public void putIfPresent(String fileName){
        String defaultKey = "xfunds";
        String key = schemaList.stream().filter(fileName::contains).findFirst().orElse(defaultKey);

        if(this.get(key) != null){
            this.get(key).add(fileName);
        }else {
            this.put(key, Lists.newArrayList(fileName));
        }
    }


}
