package com.person.xue.sql.scan;

import com.person.xue.sql.component.Schema;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Created by fenming.xue on 2020/7/6.
 */
public class SqlSchemaParser {

    private Collection<File> files;

    public SqlSchemaParser(Collection<File> files){
        this.files = files;
    }

    public Schema parser(){
        if(this.files == null || this.files.isEmpty()){
            return null;
        }
        Schema schema = new Schema("ecas,sds");
        this.files.stream().map(File::getName).filter(fileName -> fileName.contains(".sql")).forEach(schema::putIfPresent);
        sort(schema);
        return schema;
    }

    private void sort(Schema schema){
        for (Map.Entry<String, Collection<String>> entry : schema.entrySet()) {
            entry.setValue(new SqlFileSorter(entry.getValue()).toSort());
        }
    }
}
