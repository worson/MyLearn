package com.sen.learn.disign.pattern.imitation.chapter16_StateMode.version1;

/**
 * Created by secon on 2016/5/2.
 */
public class WorkStateForenoon extends WorkState {
    @Override
    public void doWork(Work work) {
        if (work.getHour()<12){
            output.output("Work in the morning, full of energy ");//上午工作，精神百倍
        }else if (work.getHour()>=12){
            work.setWorkState(new WorkStateNoon());
            work.dowork();
        }
    }
}
