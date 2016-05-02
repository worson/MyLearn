package com.sen.learn.disign.pattern.imitation.chapter28;

import com.sen.learn.disign.pattern.imitation.utils.ConsoleOutput;
import com.sen.learn.disign.pattern.imitation.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 */
public abstract class VisitorModeAction {
    public IConsoleOutput output = ConsoleOutput.getInstance();

    public abstract void getManConclusion(VisitorModeMan man);
    public abstract void getWomanConclusion(VisitorModeWoman woman);
}
