package com.sen.designmode.Dahua.chapter22_BridgeMode.version1;


import com.sen.designmode.Dahua.utils.ConsoleOutput;
import com.sen.designmode.Dahua.utils.IConsoleOutput;

/**
 * Created by secon on 2016/5/2.
 */
public abstract class PhoneSoft {
    public IConsoleOutput output = ConsoleOutput.getInstance();
    public abstract void run();
}
