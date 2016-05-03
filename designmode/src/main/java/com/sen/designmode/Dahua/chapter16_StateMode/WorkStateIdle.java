package com.sen.designmode.Dahua.chapter16_StateMode;

/**
 * Created by secon on 2016/5/2.
 */
public class WorkStateIdle extends WorkState {
    @Override
    public void doWork(Work work) {
        work.setWorkState(new WorkStateForenoon());
    }
}
