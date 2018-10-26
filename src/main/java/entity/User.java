package entity;/*
 *  @author:Logan XU
 *  @Date:Created in 22:09_2018/10/23
 *  @Modified by:
 *  
*/

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
