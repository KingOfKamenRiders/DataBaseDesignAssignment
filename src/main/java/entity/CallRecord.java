package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 18:24_2018/10/24
 *  @Modified by:
 *  
*/

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
public class CallRecord implements Serializable{
    @Id
    private long callerId;
    @Id
    private long calleeId;
    @Id
    private LocalDateTime time;
    private int duration = 0;

    public CallRecord(long callerId, long calleeId, LocalDateTime time, int duration) {
        this.callerId = callerId;
        this.calleeId = calleeId;
        this.time = time;
        this.duration = duration;
    }
    public CallRecord(){}
    public long getCallerId() {
        return callerId;
    }

    public void setCallerId(long callerId) {
        this.callerId = callerId;
    }

    public long getCalleeId() {
        return calleeId;
    }

    public void setCalleeId(long calleeId) {
        this.calleeId = calleeId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
