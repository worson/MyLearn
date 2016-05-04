package com.sen.learn.disign.pattern.imitation.chapter16_StateMode.version1;

/**
 * Created by secon on 2016/5/2.
 * 更容易做到责任分解
 */
public class StateModeClient {
    public static void main(String[] args) {
        Work work = new Work();
        work.setHour(8);
        work.dowork();
        work.setHour(8);
        work.dowork();
        work.setHour(11);
        work.dowork();
        work.setHour(12);
        work.dowork();
        work.setHour(14);
        work.dowork();
        work.setHour(18);
        work.dowork();
        work.setHour(22);
        work.dowork();
    }
}
