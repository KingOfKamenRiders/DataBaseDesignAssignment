package util;/*
 *  @author:Logan XU
 *  @Date:Created in 21:20_2018/10/24
 *  @Modified by:
 *  
*/

import java.io.*;
import java.util.Properties;

public class ConfigReader {
    public static double callCharge = 0;
    public static double messageCharge = 0;
    public static double localDataCharge = 0;
    public static double nationDataCharge = 0;
    static {
        try{
            InputStream is = ConfigReader.class.getResourceAsStream("/config.properties");
            Properties properties =  new Properties();
            properties.load(is);
            callCharge = Double.parseDouble(properties.getProperty("callCharge"));
            messageCharge = Double.parseDouble(properties.getProperty("messageCharge"));
            localDataCharge = Double.parseDouble(properties.getProperty("localDataCharge"));
            nationDataCharge = Double.parseDouble(properties.getProperty("nationDataCharge"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
