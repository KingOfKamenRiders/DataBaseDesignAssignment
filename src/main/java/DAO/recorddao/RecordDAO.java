package DAO.recorddao;/*
 *  @author:Logan XU
 *  @Date:Created in 10:14_2018/10/25
 *  @Modified by:
 *  
*/

import entity.TaoCan;
import util.ResultMessage;

import javax.xml.transform.Result;
import java.time.LocalDateTime;

public interface RecordDAO {
    ResultMessage recordCall(long callerId, long calleeId, LocalDateTime time, int Duration);
    ResultMessage recordMessage(long senderId,long receiverId,LocalDateTime time, String content);
    ResultMessage recordLocalData(long userId,LocalDateTime time,long volume);
    ResultMessage recordNationData(long userId,LocalDateTime time,long volume);
    ResultMessage subscribeTaoCan(long userId, long tid,LocalDateTime today,boolean immediate);
    ResultMessage unsubscribeTaoCan(long userId , long tid,LocalDateTime today,boolean immediate);
}
