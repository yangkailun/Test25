package com.example.hp.test25.util;

/**
 * Created by HP on 2018-04-16.
 */

public class TimeUti {
    public static String outPutTime(int time){
        int year = time/10000;
        int month = (time%10000)/100;
        int day = time%100;
        return year+"."+month+"."+day;
    }
}
