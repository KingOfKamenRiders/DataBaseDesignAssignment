package DAO.recorddao;/*
 *  @author:Logan XU
 *  @Date:Created in 10:15_2018/10/25
 *  @Modified by:
 *  
*/

import entity.*;
import org.hibernate.criterion.Order;
import singleton.EMFactory;
import util.ResultMessage;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class RecordDAOImp implements RecordDAO{
    private EntityManager em = EMFactory.getEmf().createEntityManager();

    @Override
    public ResultMessage recordCall(long callerId, long calleeId, LocalDateTime time, int Duration) {
        CallRecord record =  new CallRecord(callerId,calleeId,time,Duration);
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    @Override
    public ResultMessage recordMessage(long senderId, long receiverId, LocalDateTime time, String content) {
        MessageRecord record = new MessageRecord(senderId,receiverId,time, content);
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    @Override
    public ResultMessage recordLocalData(long userId, LocalDateTime time, long volume) {
        LocalDataRecord record = new LocalDataRecord(userId,time,volume);
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    @Override
    public ResultMessage recordNationData(long userId, LocalDateTime time, long volume) {
        NationDataRecord record = new NationDataRecord(userId,time,volume);
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    /**
     * 如果该客户已经订购了某套餐（且没有退订），则不能再次订购
     * @param userId 订购者ID
     * @param tid 套餐ID
     * @param today 订购日期
     * @param immediate 是否立即生效
     * @return
     */
    @Override
    public ResultMessage subscribeTaoCan(long userId, long tid,LocalDateTime today ,boolean immediate) {
        em.getTransaction().begin();
        User user = em.find(User.class,userId);
        TaoCan t = em.find(TaoCan.class,tid);
        if(user!=null && t!=null){
            Query q1 = em.createQuery("from User_TaoCan where user = :user and taoCan = :taoCan");
            q1.setParameter("user",user);
            q1.setParameter("taoCan",t);
            if(q1.getResultList().size()==0){
                LocalDateTime effectiveTime = today.plusSeconds(0);
                if(!immediate){
                    effectiveTime = effectiveTime.plusMonths(1);
                    effectiveTime = effectiveTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(1);
                }
                User_TaoCan ut =  new User_TaoCan(user,t,effectiveTime);
                em.persist(ut);
                SubscribeRecord record = new SubscribeRecord(userId,t.getId(),today,effectiveTime,true);
                em.persist(record);
            }
        }
        em.getTransaction().commit();
        return ResultMessage.OK;
    }

    /**
     * 
     * @param userId 订购者Id
     * @param tid 套餐ID
     * @param today 订购日期
     * @param immediate 是否立即生效
     * @return
     */
    @Override
    public ResultMessage unsubscribeTaoCan(long userId, long tid,LocalDateTime today,boolean immediate) {
        em.getTransaction().begin();
        User user = em.find(User.class,userId);
        TaoCan t = em.find(TaoCan.class,tid);
        if(user!=null && t!=null){
            Query q1 = em.createQuery("from User_TaoCan where user = :user and taoCan = :taoCan");
            q1.setParameter("user",user);
            q1.setParameter("taoCan",t);
            List<User_TaoCan> uts = q1.getResultList();
            if(uts.size()>0){
                LocalDateTime effectiveTime = today.plusSeconds(0);
                if(!immediate){
                    effectiveTime = effectiveTime.plusMonths(1);
                    effectiveTime = effectiveTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(1);
                }
                SubscribeRecord record = new SubscribeRecord(userId,t.getId(),today,effectiveTime,false);
                em.persist(record);
                em.remove(uts.get(0));
            }
        }
        em.getTransaction().commit();
        return ResultMessage.OK;
    }
}
