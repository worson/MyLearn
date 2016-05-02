package com.sen.learn.disign.pattern.imitation.chapter28;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeClient {
    public static void main(String[] args) {
        System.out.println("***************访问者模式示例：男人和女人*************************");
        VisitorModeManager manager = new VisitorModeManager();
        manager.attach(new VisitorModeMan());
        manager.attach(new VisitorModeWoman());

        manager.display(new VisitorModeSuccess());
    }
}
