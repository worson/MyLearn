package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeSuccess extends VisitorModeAction {
    @Override
    public void getManConclusion(com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode.VisitorModeMan man) {
        output.output(man.getSex()+"�ɹ�ʱ"+"��������һ��ΰ���Ů��");
    }

    @Override
    public void getWomanConclusion(VisitorModeWoman woman) {
        output.output(woman.getSex()+"�ɹ�ʱ"+"�������һ�����ɹ�������");
    }
}
