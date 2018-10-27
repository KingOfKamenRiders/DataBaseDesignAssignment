package DAO.userdao;/*
 *  @author:Logan XU
 *  @Date:Created in 19:50_2018/10/24
 *  @Modified by:
 *  
*/

import entity.*;
import singleton.EMFactory;
import util.ChargeType;
import util.ConfigReader;
import util.ResultMessage;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class UserDAOImp implements UserDAO {
    private EntityManager em = EMFactory.getEmf().createEntityManager();

    private List<TaoCan> getAvailableTaoCans(User user,LocalDateTime today){
        Query q1 = em.createQuery(" from User_TaoCan  where user = :user and effectiveTime <= :today");
        q1.setParameter("user",user);
        q1.setParameter("today",today);
        List<User_TaoCan> uts = q1.getResultList();
        List<TaoCan> result = new ArrayList<>();
        for(User_TaoCan ut:uts)
            result.add(ut.getTaoCan());
        return result;
    }


    @Override
    public User getUser(long id) {
        return em.find(User.class,id);
    }



    @Override
    public Bill getBill(long userId, int year, int month) {
        em.getTransaction().begin();
        Query query = em.createQuery(" from Bill where userId = :userId and year = :year and month = :month");
        query.setParameter("userId",userId);
        query.setParameter("year",year);
        query.setParameter("month",month);
        Object bill = query.getSingleResult();
        if(bill == null){

        }else{
            return (Bill)bill;
        }
        return null;
    }

    @Override
    public long getUsedCall(long userId, LocalDateTime today) {
        User user = em.find(User.class,userId);
        if(user!=null){
            List<TaoCan> taoCans = getAvailableTaoCans(user,today);
            int maxCall = 0;
            for(TaoCan t :taoCans){
                maxCall+=t.getMaxCall();
            }

            Query query = em.createQuery("select sum (duration) from CallRecord" +
                    " where callerId =:userId and year(time) = :year and month(time)= :month");
            query.setParameter("userId",userId);
            query.setParameter("year",today.getYear());
            query.setParameter("month",today.getMonthValue());
            if(query.getSingleResult()==null)
                return 0;
            long totalDuration = (long)query.getSingleResult();

            if(totalDuration>maxCall)
                return (totalDuration-maxCall);
            else
                return 0;
        }
        return 0;
    }

    @Override
    public long getUsedMessage(long userId, LocalDateTime today) {
        User user = em.find(User.class,userId);
        if(user != null){
            List<TaoCan> taoCans = getAvailableTaoCans(user,today);
            int maxMessages = 0;
            for(TaoCan t :taoCans){
                maxMessages+=t.getMaxMessage();
            }
            Query query = em.createQuery("select count (MessageRecord) from MessageRecord" +
                    " where senderId =:userId and year(time) = :year and month(time)= :month");
            query.setParameter("userId",userId);
            query.setParameter("year",today.getYear());
            query.setParameter("month",today.getMonthValue());
            if(query.getSingleResult() == null)
                return 0;
            long totalMessages = (long)query.getSingleResult();
            if(totalMessages>maxMessages)
                return (totalMessages-maxMessages);
            else
                return 0;
        }
        return 0;
    }

    @Override
    public long getUsedLocalData(long userId, LocalDateTime today) {
        User user = em.find(User.class,userId);
        if(user != null){
            List<TaoCan> taoCans = getAvailableTaoCans(user,today);
            int maxLocalData = 0;
            for(TaoCan t :taoCans){
                maxLocalData+=t.getMaxLocalData();
            }

            Query query = em.createQuery("select sum (volume) from LocalDataRecord" +
                    " where userId =:userId and year(time) = :year and month(time)= :month");
            query.setParameter("userId",userId);
            query.setParameter("year",today.getYear());
            query.setParameter("month",today.getMonthValue());
            if(query.getSingleResult() == null)
                return 0;
            long totalLocalData = (long) query.getSingleResult();
            if(totalLocalData>maxLocalData)
                return (totalLocalData - maxLocalData);
            else
                return 0;
        }
        return 0;
    }

    @Override
    public long getUsedNationData(long userId, LocalDateTime today) {
        User user = em.find(User.class,userId);
        if(user != null){
            List<TaoCan> taoCans = getAvailableTaoCans(user,today);
            int maxNationData = 0;
            for(TaoCan t :taoCans){
                maxNationData+=t.getMaxNationData();
            }

            Query query = em.createQuery("select sum (volume) from NationDataRecord" +
                    " where userId =:userId and year(time) = :year and month(time)= :month");
            query.setParameter("userId",userId);
            query.setParameter("year",today.getYear());
            query.setParameter("month",today.getMonthValue());
            if(query.getSingleResult()==null)
                return 0;
            long totalNationData = (long) query.getSingleResult();
            if(totalNationData>maxNationData)
                return (totalNationData - maxNationData);
            else
                return 0;
        }
        return 0;
    }

    @Override
    public double getCallCost(long userId,LocalDateTime today) {
        return getUsedCall(userId,today)*ConfigReader.callCharge;
    }

    @Override
    public double getMessageCost(long userId,LocalDateTime today) {
       return getUsedMessage(userId, today)*ConfigReader.messageCharge;
    }

    @Override
    public double getLocalDataCost(long userId,LocalDateTime today) {
       return getUsedLocalData(userId, today)*ConfigReader.localDataCharge;
    }

    @Override
    public double getNationDataCost(long userId,LocalDateTime today) {
        return getUsedNationData(userId, today)*ConfigReader.nationDataCharge;
    }

    @Override
    public List<SubscribeRecord> getSubscribeHistory(long userId) {
        em.getTransaction().begin();
        Query query = em.createQuery(" from SubscribeRecord where userId = :userId");
        query.setParameter("userId",userId);
        List<SubscribeRecord> records = query.getResultList();
        em.getTransaction().commit();
        return records;
    }

    /**
     * 生成账单的方法
     * 如果本月不是第一次生成账单，将会先删除上次生成的账单，然后根据当前的用量和套餐情况重新生成最新的账单
     * @param userId
     * @param today
     * @return
     */
    @Override
    public ResultMessage generateBill(long userId,LocalDateTime today) {

        User user = em.find(User.class,userId);
        if(user == null)
            return ResultMessage.ERROR;
        em.getTransaction().begin();
        Bill bill = em.find(Bill.class,new BillKey(userId,today.getYear(),today.getMonthValue()));
        if(bill != null) {
            em.remove(bill);
        }
        bill = new Bill(userId, today.getYear(), today.getMonthValue());
        List<TaoCan> taoCans = getAvailableTaoCans(user,today);
        for(TaoCan t:taoCans){
            bill.getItems().add(new BillItem(ChargeType.TAOCAN,t.getPrice()));
        }
        double callCost = getCallCost(userId,today);
        if(callCost>0)
            bill.getItems().add(new BillItem(ChargeType.CALL,callCost));
        double messageCost = getMessageCost(userId,today);
        if(messageCost>0)
            bill.getItems().add(new BillItem(ChargeType.MESSAGE,messageCost));
        double localDataCost = getLocalDataCost(userId,today);
        if(localDataCost>0)
            bill.getItems().add(new BillItem(ChargeType.LOCALDATA,localDataCost));
        double nationDataCost = getNationDataCost(userId,today);
        if(nationDataCost>0)
            bill.getItems().add(new BillItem(ChargeType.NATIONDATA,nationDataCost));
        em.persist(bill);
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    @Override
    public List<User_TaoCan> getCurrentTaoCan(long userId) {
        Query query = em.createQuery("from User_TaoCan where user = :user");
        query.setParameter("user",em.find(User.class,userId));
        return query.getResultList();
    }
}
