package com.sen.learn.disign.pattern.imitation.chapter22_BridgeMode.version1;

import com.sen.learn.disign.pattern.imitation.utils.ConsoleOutput;
import com.sen.learn.disign.pattern.imitation.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 */
public abstract class PhoneSoft {
    public IConsoleOutput output = ConsoleOutput.getInstance();
    public abstract void run();
}
