package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 19:03_2018/10/24
 *  @Modified by:
 *  
*/

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

public class SubscribeRecord implements Serializable{
    @Id
    private long userId;
    @Id
    private long TaoCanId;
    @Id
    private LocalDateTime operateTime;
    private LocalDateTime effectiveTime;
    private boolean isSubscribe;

    public SubscribeRecord(long userId, long taoCanId, LocalDateTime operateTime, LocalDateTime effectiveTime, boolean isSubscribe) {
        this.userId = userId;
        TaoCanId = taoCanId;
        this.operateTime = operateTime;
        this.effectiveTime = effectiveTime;
        this.isSubscribe = isSubscribe;
    }

    public SubscribeRecord() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTaoCanId() {
        return TaoCanId;
    }

    public void setTaoCanId(long taoCanId) {
        TaoCanId = taoCanId;
    }

    public LocalDateTime getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(LocalDateTime operateTime) {
        this.operateTime = operateTime;
    }

    public LocalDateTime getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(LocalDateTime effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean subscribe) {
        isSubscribe = subscribe;
    }
}
