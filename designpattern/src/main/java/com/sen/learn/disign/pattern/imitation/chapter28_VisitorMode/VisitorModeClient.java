package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeClient {
    public static void main(String[] args) {
        System.out.println("***************������ģʽʾ�������˺�Ů��*************************");
        VisitorModeManager manager = new VisitorModeManager();
        manager.attach(new VisitorModeMan());
        manager.attach(new VisitorModeWoman());
        //����Ҫ����һ������ʱ��ֻ��Ҫ����һ��VisitorModeAction
        manager.display(new VisitorModeSuccess());
    }
}
