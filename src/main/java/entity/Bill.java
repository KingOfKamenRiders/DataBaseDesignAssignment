package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 19:27_2018/10/24
 *  @Modified by:
 *  
*/

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(BillKey.class)
public class Bill implements Serializable{


    @ElementCollection
    private Set<BillItem> items = new HashSet<>();

    @Id
    private long userId;
    @Id
    private int year;
    @Id
    private int month;

    public Bill(long userId, int year, int month) {
        this.userId = userId;
        this.year = year;
        this.month = month;
    }

    public Bill() {
    }


    public Set<BillItem> getItems() {
        return items;
    }

    public void setItems(Set<BillItem> items) {
        this.items = items;
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
