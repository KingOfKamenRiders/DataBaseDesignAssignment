package util;/*
 *  @author:Logan XU
 *  @Date:Created in 19:31_2018/10/24
 *  @Modified by:
 *  
*/

public enum ChargeType {
    TAOCAN, MESSAGE, CALL, LOCALDATA, NATIONDATA,;

    @Override
    public String toString() {
        switch (this.ordinal()){
            case 0:
                return "套餐";
            case 1:
                return "短信";
            case 2:
                return "通话";
            case 3:
                return "本地流量";
            case 4:
                return "国内流量";
        }
        return this.name();
    }
}
