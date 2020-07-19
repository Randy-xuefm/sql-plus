package com.erayt.xfunds.sql.scan;

import com.erayt.xfunds.sql.component.Days;
import com.erayt.xfunds.sql.component.DbType;
import com.erayt.xfunds.sql.config.PatchConfig;
import com.erayt.xfunds.sql.utils.PathUtils;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by fenming.xue on 2020/6/28.
 */
@Component
public class SqlFileScanner implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(SqlFileScanner.class);

    private PatchConfig config;

    public SqlFileScanner(PatchConfig config){
        this.config = config;
    }

    public List<File> scan() throws IOException {
        File file = new File(this.config.getScanPackage());

        if(!file.isDirectory()){
            this.logger.warn("xfunds.sql.patch.scanPackage配置错误,{}",this.config.getScanPackage());
            return null;
        }

        List<File> sqlDirList = findSqlPath(file);
        this.logger.info("扫描到sql目录:{}",sqlDirList);

        List<File> dbDirList = findDbPath(sqlDirList);
        this.logger.info("扫描到{}目录:{}",DbType.get(this.config.getDbType()),dbDirList);

        List<File> patchDirList = findPatchPath(dbDirList);
        this.logger.info("扫描到patch目录:{}",patchDirList);

        List<File> sqlFileList = findSqlFiles(patchDirList);
        this.logger.info("扫描到sql文件:{}",sqlFileList);

        copy2TargetPath(sqlFileList);


        return getTargetPathFiles();
    }

    private List<File> getTargetPathFiles(){
        File file = new File(this.config.getTargetPackage());

        return Arrays.stream(Objects.requireNonNull(file.listFiles())).collect(Collectors.toList());
    }

    private void copy2TargetPath(List<File> sqlFileList){
        String targetDir =  this.config.getTargetPackage();
        targetDir = PathUtils.cleanPath(targetDir);
        File dir = new File(targetDir);
        if(dir.exists()){
            File[] exists = dir.listFiles();
            Arrays.stream(Optional.ofNullable(exists).orElse(new File[1])).forEach(file -> file.delete());
        }else{
            Assert.state(dir.mkdir(),"创建文件路径失败," + targetDir);
        }
        Set<String> fileNames  = new HashSet<>();
        for (File file : sqlFileList) {
            try {
                String fileName = file.getName();
                if(fileNames.add(file.getName())){
                    fileName = fileName + UUID.randomUUID().toString();
                }
                Files.copy(file,new File(targetDir + File.separator + fileName));
            } catch (IOException e) {
                this.logger.info("文件复制失败,{}{}",file.getAbsolutePath(),file.getName());
            }
        }
    }

    private List<File> findSqlPath(File file){
        File[] files = file.listFiles();
        if(files == null){
            return Lists.newArrayList();
        }
        return findPath(Arrays.stream(files).collect(Collectors.toList()), "sql");
    }

    private List<File> findDbPath(List<File> sqlDirList){
        String dbDirName = DbType.get(this.config.getDbType());
        return findPath(sqlDirList,dbDirName);
    }

    private List<File> findPatchPath(List<File> dbDirList){
        return findPath(dbDirList,"patch");
    }

    private List<File> findSqlFiles(List<File> patchDirList) throws IOException {
        Integer startDate = Optional.ofNullable(LastDateWriter.getLast(this.config.getDbType())).orElse(this.config.getLastDate());

        Days days = new Days(startDate);
        List<File> monthDirList = findPath(patchDirList,dir -> days.matchDir(dir.getName()));
        this.logger.info("扫描到日期目录,{}",monthDirList);

        List<File> sqlFileList = new ArrayList<>();

        monthDirList.forEach(dir -> sqlFileList.addAll(Arrays.stream(Optional.ofNullable(dir.listFiles(filter -> days.match(filter.getName()) && filter.getName().contains(".sql")))
                                                                               .orElse(new File[1])).collect(Collectors.toList())));

        return sqlFileList;
    }

    private List<File> findPath(List<File> fileList, Predicate<File> predicate){
        List<File> dirList = new ArrayList<>();
        if(fileList == null || fileList.isEmpty()){
            return dirList;
        }
        fileList.parallelStream().forEach(subDir ->{
            if(!subDir.isDirectory()){
                return;
            }
            if(StringUtils.hasLength(this.config.getExcludeFilter()) && this.config.getExcludeFilter().contains(subDir.getName())){
                return;
            }
            if(predicate.test(subDir)){
                dirList.add(subDir);
                return;
            }
            File[] childrenDirs = subDir.listFiles();
            if(childrenDirs == null){
                return;
            }
            dirList.addAll(findPath(Arrays.stream(childrenDirs).collect(Collectors.toList()), predicate));
        });

        return dirList;
    }

    private List<File> findPath(List<File> fileList,String matchDirName){
        return findPath(fileList, dir -> dir.getName().equals(matchDirName));
    }

    @Override
    public void afterPropertiesSet() {
        Assert.state(config != null,"增量扫描配置文件不允许为空!");
        Assert.state(StringUtils.hasLength(this.config.getScanPackage()),"xfunds.sql.patch.scanPackage不允许为空!");
        Assert.state(this.config.getLastDate() != null,"xfunds.sql.patch.lastDate不允许为空!");
        Assert.state(StringUtils.hasLength(this.config.getTargetPackage()),"xfunds.sql.patch.targetPackage不允许为空");

    }
}

