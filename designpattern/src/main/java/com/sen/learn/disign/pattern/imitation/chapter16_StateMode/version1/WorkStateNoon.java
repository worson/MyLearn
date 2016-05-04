package com.sen.learn.disign.pattern.imitation.chapter16_StateMode.version1;

/**
 * Created by secon on 2016/5/2.
 */
public class WorkStateNoon extends WorkState {
    @Override
    public void doWork(Work work) {
        if (work.getHour()<13){
            output.output("Hungry, lunch break; sleepy, lunch break; ");//饿了，午休；犯困，午休；
        }else if (work.getHour()>=13){
            work.setWorkState(new WorkStateAfterNoon());
            work.dowork();
        }

    }
}
