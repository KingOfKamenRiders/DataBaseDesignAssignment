package singleton;/*
 *  @author:Logan XU
 *  @Date:Created in 20:21_2018/10/24
 *  @Modified by:
 *  
*/

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
    private static EMFactory ourInstance = new EMFactory();
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");

    public static EMFactory getInstance() {

        return ourInstance;
    }

    public static EntityManagerFactory getEmf(){
        return emf;
    }
    private EMFactory() {
    }
}
