package com.sen.learn.disign.pattern.imitation.chapter16_StateMode.version1;

import com.sen.learn.disign.pattern.imitation.utils.ConsoleOutput;
import com.sen.learn.disign.pattern.imitation.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 */
public abstract class WorkState {
    public IConsoleOutput output = ConsoleOutput.getInstance();
    public abstract void doWork(Work work);
}
