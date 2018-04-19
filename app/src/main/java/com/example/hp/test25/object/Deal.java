package com.example.hp.test25.object;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018-04-16.
 */

public class Deal extends DataSupport{

    public static final int INCOME = 0;
    public static final int EXPENSES = 1;

    public static final int CLOTHES = 2;
    public static final int FOOD = 3;
    public static final int HOME = 4;
    public static final int WALK = 5;
    public static final int OTHER = 6;
    public static final int PROFESSION = 7;
    public static final int INVEST = 8;
    public static final int OTHERS = 9;

    private int id;    //存储在数据库的主键
    private int direction;     //方向:收入或支出
    private float money;
    private int type;   //类型：衣食住行其他;
    private String remark;
    private int time;   //时间：保存秒数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
