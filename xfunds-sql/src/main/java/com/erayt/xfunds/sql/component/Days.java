package com.erayt.xfunds.sql.component;

import org.springframework.util.Assert;

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

    public boolean match(String fileName){
        int index = fileName.indexOf(getYearAndMonth());
        if(index == -1){
            return false;
        }
        String date = fileName.substring(index, index + 8);

        int day = 0;
        try {
            day = Integer.parseInt(date.substring(6,8));
        } catch (NumberFormatException e) {
           //ignore
        }
        return day > this.day;
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
