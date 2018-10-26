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
public class LocalDataRecord implements Serializable{
    @Id
    private long userId;
    @Id
    private LocalDateTime time;
    private long volume;

    public LocalDataRecord(long userId, LocalDateTime time, long volume) {
        this.userId = userId;
        this.time = time;
        this.volume = volume;
    }

    public LocalDataRecord() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
