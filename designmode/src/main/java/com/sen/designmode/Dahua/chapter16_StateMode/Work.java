package com.sen.designmode.Dahua.chapter16_StateMode;

/**
 * Created by secon on 2016/5/2.
 */
public class Work {
    private boolean isFinish;
    private WorkState workState;
    private int hour;

    public Work() {
        this.workState = new WorkStateIdle();
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public WorkState getWorkState() {
        return workState;
    }

    public void setWorkState(WorkState workState) {
        this.workState = workState;
    }

    public void dowork(){
        workState.doWork(this);
    }

}
