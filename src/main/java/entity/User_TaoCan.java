package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 13:54_2018/10/26
 *  @Modified by:
 *  
*/

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
public class User_TaoCan implements Serializable{
    @Id
    @ManyToOne
    private User user;
    @Id
    @ManyToOne
    private TaoCan taoCan;
    private LocalDateTime effectiveTime;

    public User_TaoCan(User user, TaoCan taoCan, LocalDateTime effectiveTime) {
        this.user = user;
        this.taoCan = taoCan;
        this.effectiveTime = effectiveTime;
    }

    public User_TaoCan() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaoCan getTaoCan() {
        return taoCan;
    }

    public void setTaoCan(TaoCan taoCan) {
        this.taoCan = taoCan;
    }

    public LocalDateTime getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(LocalDateTime effectiveTime) {
        this.effectiveTime = effectiveTime;
    }
}
