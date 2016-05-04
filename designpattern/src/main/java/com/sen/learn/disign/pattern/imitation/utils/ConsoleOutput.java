package com.sen.learn.disign.pattern.imitation.utils;

/**
 * Created by secon on 2016/5/2.
 */
public class ConsoleOutput implements IConsoleOutput {
    private static ConsoleOutput ourInstance = new ConsoleOutput();

    public static ConsoleOutput getInstance() {
        return ourInstance;
    }

    private ConsoleOutput() {
    }

    @Override
    public int output(String msg) {
        System.out.println(msg);
        return 0;
    }
}
