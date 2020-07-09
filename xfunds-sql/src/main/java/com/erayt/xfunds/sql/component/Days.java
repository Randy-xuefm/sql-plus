package com.erayt.xfunds.sql.component;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by fenming.xue on 2020/6/28.
 */
public class Days {

    private String date;

    private Integer year;

    private Integer month;

    private Integer day;

    public Days(Integer date){
        Assert.state(date != null,"Days构造函数参数为null");
        String tempDate = date.toString();
        Assert.state(tempDate.length() == 8,"日期格式错误!");
        this.date = tempDate;
        this.year = Integer.parseInt(tempDate.substring(0,4));
        this.month = Integer.parseInt(tempDate.substring(4,6));
        this.day = Integer.parseInt(tempDate.substring(6,8));
    }

    public Days(String fileName){
        Assert.state(StringUtils.hasLength(fileName),"文件名错误,无法解析日期" + fileName);
        String date = findDate(fileName);
        Assert.state(date.length() == 8,"文件名错误,无法解析日期" + fileName);
        this.date = date;
        this.year = Integer.parseInt(date.substring(0,4));
        this.month = Integer.parseInt(date.substring(4,6));
        this.day = Integer.parseInt(date.substring(6,8));
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getYearAndMonth(){
        if(this.month < 10){
            return this.year + "0" +this.month;
        }
        return this.year + "" + this.month;
    }

    public boolean matchDir(String dirName){
        return Pattern.compile("[0-9]*").matcher(dirName).matches() &&
                this.year <= Integer.parseInt(dirName.substring(0, 4)) &&
                this.month <= Integer.parseInt(dirName.substring(4, 6));
    }

    public boolean match(String fileName){
        return compare(new Days(fileName)) <= 0;
    }

    private String findDate(String fileName){
        fileName = StringUtils.stripFilenameExtension(fileName);
        String[] parts = fileName.split("_");
        Assert.state(parts.length > 0, "文件名格式错误," +fileName);

        return Arrays.stream(parts)
                .filter(part -> Pattern.compile("[0-9]*").matcher(part).matches() && part.length() == 8)
                .findFirst()
                .orElse("");
    }

    public int compare(Days target){
        return LocalDate.of(this.year,this.month,this.day).compareTo(LocalDate.of(target.getYear(), target.getMonth(), target.getDay()));
    }

    @Override
    public String toString() {
        return "Days{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

}
