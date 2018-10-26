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
public class MessageRecord implements Serializable{
    @Id
    private long senderId;
    private long receiverId;
    @Id
    private LocalDateTime time;
    private String content;

    public MessageRecord(long senderId, long receiverId, LocalDateTime time, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = time;
        this.content = content;
    }

    public MessageRecord() {
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
