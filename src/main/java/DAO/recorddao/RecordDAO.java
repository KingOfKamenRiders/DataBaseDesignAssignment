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

/**
 * 该接口实现用户平时通话、使用流量、订购/退订套餐的记录的功能
 */
public interface RecordDAO {
    /**
     *
     * @param callerId 呼叫人ID
     * @param calleeId 被呼叫人ID
     * @param time 通话发生的时间
     * @param Duration 通话持续的时间
     * @return
     */
    ResultMessage recordCall(long callerId, long calleeId, LocalDateTime time, int Duration);

    /**
     *
     * @param senderId 发送人ID
     * @param receiverId 收信人ID
     * @param time 发送时间
     * @param content 发送内容
     * @return
     */
    ResultMessage recordMessage(long senderId,long receiverId,LocalDateTime time, String content);

    /**
     *
     * @param userId 使用人ID
     * @param time 产生时间
     * @param volume 用量
     * @return
     */
    ResultMessage recordLocalData(long userId,LocalDateTime time,long volume);

    /**
     *
     * @param userId  使用人ID
     * @param time 产生时间
     * @param volume 用量
     * @return
     */
    ResultMessage recordNationData(long userId,LocalDateTime time,long volume);

    /**
     *
     * @param userId 订购者ID
     * @param tid 套餐ID
     * @param today 订购日期
     * @param immediate 是否立即生效
     * @return
     */
    ResultMessage subscribeTaoCan(long userId, long tid,LocalDateTime today,boolean immediate);

    /**
     *
     * @param userId 订购者Id
     * @param tid 套餐ID
     * @param today 订购日期
     * @param immediate 是否立即生效
     * @return
     */
    ResultMessage unsubscribeTaoCan(long userId , long tid,LocalDateTime today,boolean immediate);
}
