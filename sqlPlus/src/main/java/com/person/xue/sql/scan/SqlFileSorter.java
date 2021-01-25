package com.person.xue.sql.scan;

import com.person.xue.sql.component.Days;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public class SqlFileSorter {

    private Collection<String> fileNames;

    public SqlFileSorter(Collection<String> fileNames){
        this.fileNames = fileNames;
    }

    public List<String> toSort(){
        List<String> resultList = new ArrayList<>();
        if(this.fileNames == null || this.fileNames.isEmpty()){
            return resultList;
        }

        return this.fileNames.stream()
                .sorted((name1, name2) -> new Days(name1).compare(new Days(name2)))
                .collect(Collectors.toList());
    }
}
