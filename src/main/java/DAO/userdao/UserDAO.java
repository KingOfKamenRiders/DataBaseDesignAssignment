package DAO.userdao;/*
 *  @author:Logan XU
 *  @Date:Created in 19:09_2018/10/24
 *  @Modified by:
 *  
*/

import entity.*;
import util.ResultMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDAO {
    User getUser(long id);

    /**
     * 获得某用户某月的最新的账单
     * 生成账单详见 generateBill 方法
     * @param userId
     * @param year
     * @param month
     * @return
     */
    Bill getBill(long userId,int year,int month);

    /**
     * 获得 today 所在的月的通话资费，下面的3个方法类似
     * @param userId
     * @param today
     * @return
     */
    double getCallCost(long userId,LocalDateTime today);
    double getMessageCost(long userId,LocalDateTime today);
    double getLocalDataCost(long userId,LocalDateTime today);
    double getNationDataCost(long userId,LocalDateTime today);

    /**
     * 获得 today 所在的月的通话总长，下面的3个方法类似
     * @param userId
     * @param today
     * @return
     */
    long getUsedCall(long userId,LocalDateTime today);
    long getUsedMessage(long userId,LocalDateTime today);
    long getUsedLocalData(long userId,LocalDateTime today);
    long getUsedNationData(long userId,LocalDateTime today);

    List<SubscribeRecord> getSubscribeHistory(long userId);
    List<User_TaoCan> getCurrentTaoCan(long userId);

    /**
     * 生成账单的方法
     * @param userId
     * @param today
     * @return
     */
    ResultMessage generateBill(long userId,LocalDateTime today);

}
