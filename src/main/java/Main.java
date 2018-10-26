import DAO.recorddao.RecordDAO;
import DAO.recorddao.RecordDAOImp;
import DAO.userdao.UserDAO;
import DAO.userdao.UserDAOImp;

import entity.*;
import singleton.EMFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


public class Main {
    private static EntityManager em = EMFactory.getEmf().createEntityManager();
    private static long userId = 1;
    private static UserDAO userDAO =  new UserDAOImp();
    private static RecordDAO recordDAO = new RecordDAOImp();
    private static Random rand =  new Random(LocalDateTime.now().getSecond());

    /**
     * 用户数据和套餐数据 使用 generateTestData() 方法生成
     * 用户通话、短信、流量的使用记录 由 generateRecord() 方法生成
     * main 方法中调用的所有测试方法 都只针对 id 为 1 的用户[题目中"某个用户"]
     * @param args
     */
    public static void main(String[] args){
        //如果 数据库无法导入数据，请uncomment下面的方法
        //generateTestData();
        //generateRecord();
        //testTaoCan();
      //  testCall();
      //  testData();
        testBill();
        EMFactory.getEmf().close();

    }

    /**
     * 该方法测试 id 为 1 的用户的套餐查询、订购、退订
     * 该方法为后续的测试方法生成一些数据
     */
    static void testTaoCan(){
        //首先模拟该用户 2018 年 1 月的使用情况
        LocalDateTime today = LocalDateTime.now();
        today = today.withDayOfYear(1);
        for(int i=0;i<28;i++){
            recordDAO.recordCall(userId,(long)2,today,rand.nextInt(100));
            recordDAO.recordLocalData(userId,today,rand.nextInt(200));
            recordDAO.recordNationData(userId,today,rand.nextInt(200));
            today = today.plusDays(1);
        }
        //查询一个用户当前订购的套餐
        long startTime = System.currentTimeMillis();
        List<User_TaoCan> user_taoCans = userDAO.getCurrentTaoCan(userId);
        String[] items = {"最长通话","最多短信","最多本地流量","最多国内流量"};
        String[] units = {"分钟","条","M","M"};
        long[] used = {userDAO.getUsedCall(userId,today),userDAO.getUsedMessage(userId,today),
                userDAO.getUsedLocalData(userId,today),userDAO.getUsedNationData(userId,today)};
        int[] maxVolumes = {0,0,0,0};
        for(User_TaoCan utc:user_taoCans){
            TaoCan t = utc.getTaoCan();
            System.out.print("套餐编号:");
            System.out.print(t.getId());
            System.out.print("   生效时间: ");
            System.out.print(utc.getEffectiveTime());
            System.out.print("   套餐价格: ");
            System.out.println(t.getPrice()+"元");
            maxVolumes[0] = t.getMaxCall(); maxVolumes[1] = t.getMaxMessage();
            maxVolumes[2] = t.getMaxLocalData(); maxVolumes[3] = t.getMaxNationData();
            for(int i=0;i<4;i++){
                System.out.print(items[i]+": ");
                System.out.print(maxVolumes[i]+units[i]);
                System.out.print("  已使用: ");
                if(used[i]>t.getMaxCall()){
                    System.out.println(maxVolumes[i]+units[i]);
                    used[i] -= t.getMaxCall();
                }else {
                    System.out.println(used[i]+units[i]);
                    used[i] = 0;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("查询时间: "+(double)(endTime-startTime)/1000+" sec.\n\n\n");

        long tid1 = (long)rand.nextInt(80)+1;
        long tid2 = (long)rand.nextInt(80)+1;

        startTime = System.currentTimeMillis();
        //订购一个套餐，立即生效
        recordDAO.subscribeTaoCan(userId,tid1,today,true);
        //订购一个套餐，下月生效
        recordDAO.subscribeTaoCan(userId,tid2,today,false);
        //退订一个套餐，立即生效
        recordDAO.unsubscribeTaoCan(userId,tid1,today.plusDays(5),true);
        //退订一个套餐，下月生效
        recordDAO.unsubscribeTaoCan(userId,tid2,today.plusDays(5),true);
        //查询套餐订购记录
        List<SubscribeRecord> records = userDAO.getSubscribeHistory(userId);
        for(SubscribeRecord record:records){
            System.out.print("套餐编号 = " + record.getTaoCanId());
            System.out.print("   操作时间 = " + record.getOperateTime());
            System.out.print("   生效时间 = " + record.getEffectiveTime());
            System.out.println("  订购/退购 = " + (record.isSubscribe()?"订购":"退购"));
        }
        endTime = System.currentTimeMillis();
        System.out.println("查询时间: "+(double)(endTime-startTime)/1000+" sec.\n\n\n");

    }

    /**
     * 该方法测试 id 为 1 的用户的通话资费的生成
     */
    static void testCall(){
        //首先模拟用户的通话记录
        long startTime = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        today = today.withDayOfYear(1);
        for(int i=0;i<28;i++){
            recordDAO.recordCall(userId,(long)2,today,rand.nextInt(100));
            today = today.plusDays(1);
        }
        //获得用户 该月的通话资费(若能被套餐cover则为0)
        double fee = userDAO.getCallCost(userId,today);
        System.out.println("通话资费 = "+fee+"元");
        long endTime = System.currentTimeMillis();
        System.out.println("查询时间: "+(double)(endTime-startTime)/1000+" sec.\n\n\n");

    }

    /**
     * 该方法测试 id 为 1 的用户的流量资费的生成
     * 如果套餐能cover这些流量，则流量资费为0
     */
    static void testData(){
        long startTime = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        today = today.withDayOfYear(1);
        for(int i=0;i<28;i++){
            recordDAO.recordLocalData(userId,today,rand.nextInt(200));
            recordDAO.recordNationData(userId,today,rand.nextInt(200));
            today = today.plusDays(1);
        }
        //获得流量资费
        double localFee = userDAO.getLocalDataCost(userId,today);
        double nationFee = userDAO.getNationDataCost(userId,today);
        System.out.println("本地资费 = "+localFee +"  国内资费 = "+nationFee);
        long endTime = System.currentTimeMillis();
        System.out.println("查询时间: "+(double)(endTime-startTime)/1000+" sec.\n\n\n");
    }

    /**
     * 该方法测试 id 为 1 的用户的账单的生成
     * 在同一个月中，账单可生成任意多次，每次都会根据当前的各项用量和套餐计算当前的账单
     * 设定统一在每月的最后一天生成本月的账单
     * 无法生成过去某个月的账单，因为那时的套餐情况已无法知晓
     * (理论上可以根据订购历史来推算过去的套餐情况，但是没有必要，因为每个月都会生成这个月的账单)
     *
     */
    static void testBill(){
        //请先执行上面几个方法，产生数据
        //生成2018年1月的账单
        long startTime = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        today = today.withDayOfYear(1);
        userDAO.generateBill(userId,today);
        //查看账单
        Bill bill = userDAO.getBill(userId,today.getYear(),today.getMonthValue());
        System.out.println("月份： "+ bill.getYear() + " / "+bill.getMonth());
        System.out.println("账单内容");
        for(BillItem billItem:bill.getItems()){
            System.out.println("款项 : " + billItem.getType()+"  费用: "+billItem.getCost());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("查询时间: "+(double)(endTime-startTime)/1000+" sec.\n\n\n");

    }
    /**
     *该方法是我用来生成测试数据的方法
     * 包括：1000名用户
     *      100个套餐
     *      id为 1 的用户的2017年的使用情况
     */
    static void generateTestData() {
        Random random = new Random();
        em.getTransaction().begin();
        //产生1000个用户
        for (int i = 0; i < 1000; i++) {
            User u = new User();
            u.setName("User_" + i);
            em.persist(u);
        }
        //产生100个套餐
        for (int i = 0; i < 100; i++) {
            TaoCan t = new TaoCan();
            t.setMaxCall((i % 5) * 100);
            t.setMaxMessage((i % 6) * 100);
            t.setMaxLocalData((i % 7) * 100);
            t.setMaxNationData((i % 8) * 100);
            t.setPrice(random.nextInt(100) + 10);
            em.persist(t);
        }
        em.getTransaction().commit();
    }
    static void generateRecord(){
        em.getTransaction().begin();
        LocalDateTime time = LocalDateTime.now();
        time = time.minusYears(1);
        time = time.withDayOfYear(1);
        //模拟从去年到今年每一天用户id为 1 的用户的使用情况
        RecordDAO recordDAO = new RecordDAOImp();
        UserDAO userDAO = new UserDAOImp();
        TaoCan t1 = em.find(TaoCan.class,(long)5);
        TaoCan t2 = em.find(TaoCan.class,(long)10);
        for(int i= 0;i < 365;i++){
            LocalDateTime today = time.plusDays(i);
            recordDAO.recordCall((long)1,rand.nextInt(500)+10,today,rand.nextInt(20));
            recordDAO.recordMessage((long)1, rand.nextInt(500)+10,today,"today is "+today.toString());
            recordDAO.recordLocalData((long)1,today,rand.nextInt(1000));
            recordDAO.recordNationData((long)1,today,rand.nextInt(1000));
            if(today.getMonthValue()%2==1&&today.getDayOfMonth()==1){
                recordDAO.subscribeTaoCan((long)1,(long)5,today,true);
                recordDAO.subscribeTaoCan((long)1,(long)10,today,false);
            }
            if(today.getMonthValue()%2==0 && today.getDayOfMonth()==2){
                recordDAO.unsubscribeTaoCan((long)1,(long)5,today,true);
                recordDAO.unsubscribeTaoCan((long)1,(long)10,today,false);
            }
            if(today.getDayOfMonth()==28)
                userDAO.generateBill((long)1,today);
        }
        time = LocalDateTime.now().withDayOfYear(1);
        for(int i=0;i<4;i++)
            recordDAO.subscribeTaoCan((long)1,(long)(20+i),time,true);
        em.getTransaction().commit();
    }
}
