package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 14:12_2018/10/25
 *  @Modified by:
 *  
*/

import javax.persistence.Id;
import java.io.Serializable;

public class BillKey implements Serializable{

    private long userId;

    private int year;

    private int month;

    public BillKey(long userId, int year, int month) {
        this.userId = userId;
        this.year = year;
        this.month = month;
    }

    public BillKey() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
