package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 19:27_2018/10/24
 *  @Modified by:
 *  
*/

import util.ChargeType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class BillItem {
    @Enumerated(EnumType.STRING)
    private ChargeType type;
    private double cost;

    public BillItem(ChargeType type, double cost) {
        this.type = type;
        this.cost = cost;
    }

    public BillItem() {
    }

    public ChargeType getType() {
        return type;
    }

    public void setType(ChargeType type) {
        this.type = type;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
