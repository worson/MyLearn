package com.sen.learn.disign.pattern.imitation.chapter16_StateMode.version1;

/**
 * Created by secon on 2016/5/2.
 */
public class WorkStateIdle extends WorkState {
    @Override
    public void doWork(Work work) {
        work.setWorkState(new WorkStateForenoon());
    }
}
