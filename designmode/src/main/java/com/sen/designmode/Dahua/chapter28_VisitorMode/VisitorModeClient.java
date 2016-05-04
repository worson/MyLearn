package com.sen.designmode.Dahua.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeClient {
    public static void main(String[] args) {
        System.out.println("***************VisitorModeClient : man and woman*************************");
        VisitorModeManager manager = new VisitorModeManager();
        manager.attach(new VisitorModeMan());
        manager.attach(new VisitorModeWoman());
        manager.display(new VisitorModeSuccess());
    }
}
