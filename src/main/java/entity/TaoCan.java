package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 18:20_2018/10/24
 *  @Modified by:
 *  
*/

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TaoCan implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int maxCall;
    private int maxMessage;
    private int maxLocalData;
    private int maxNationData;
    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxCall() {
        return maxCall;
    }

    public void setMaxCall(int maxCall) {
        this.maxCall = maxCall;
    }

    public int getMaxMessage() {
        return maxMessage;
    }

    public void setMaxMessage(int maxMessage) {
        this.maxMessage = maxMessage;
    }

    public int getMaxLocalData() {
        return maxLocalData;
    }

    public void setMaxLocalData(int maxLocalData) {
        this.maxLocalData = maxLocalData;
    }

    public int getMaxNationData() {
        return maxNationData;
    }

    public void setMaxNationData(int maxNationData) {
        this.maxNationData = maxNationData;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
