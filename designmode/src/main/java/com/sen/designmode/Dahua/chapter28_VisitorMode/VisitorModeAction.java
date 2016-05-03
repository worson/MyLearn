package com.sen.designmode.Dahua.chapter28_VisitorMode;


import com.sen.designmode.Dahua.utils.ConsoleOutput;
import com.sen.designmode.Dahua.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 * 适用于行为是比较稳定的
 */
public abstract class VisitorModeAction {
    public IConsoleOutput output = ConsoleOutput.getInstance();

    public abstract void getManConclusion(VisitorModeMan man);
    public abstract void getWomanConclusion(VisitorModeWoman woman);
}
