package com.example.hp.test25.object;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018-04-22.
 */

public class Budget extends DataSupport{

    private int id;
    private int startTime;
    private int endTime;
    private float incomeBudget;
    private float expenseBudget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public float getIncomeBudget() {
        return incomeBudget;
    }

    public void setIncomeBudget(float incomeBudget) {
        this.incomeBudget = incomeBudget;
    }

    public float getExpenseBudget() {
        return expenseBudget;
    }

    public void setExpenseBudget(float expenseBudget) {
        this.expenseBudget = expenseBudget;
    }
}
