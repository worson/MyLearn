package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeClient {
    public static void main(String[] args) {
        System.out.println("***************访问者模式示例：男人和女�?*************************");
        VisitorModeManager manager = new VisitorModeManager();
        manager.attach(new VisitorModeMan());
        manager.attach(new VisitorModeWoman());
        //当需要增加一个特性时，只�?要增加一个VisitorModeAction
        manager.display(new VisitorModeSuccess());
    }
}
