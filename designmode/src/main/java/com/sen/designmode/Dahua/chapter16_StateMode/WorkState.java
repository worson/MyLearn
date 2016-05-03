package com.sen.designmode.Dahua.chapter16_StateMode;


import com.sen.designmode.Dahua.utils.ConsoleOutput;
import com.sen.designmode.Dahua.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 */
public abstract class WorkState {
    public IConsoleOutput output = ConsoleOutput.getInstance();
    public abstract void doWork(Work work);
}
