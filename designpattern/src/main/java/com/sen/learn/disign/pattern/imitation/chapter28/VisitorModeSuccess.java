package com.sen.learn.disign.pattern.imitation.chapter28;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeSuccess extends VisitorModeAction {
    @Override
    public void getManConclusion(VisitorModeMan man) {
        output.output(man.getSex()+"成功时"+"背后多半有一个伟大的女人");
    }

    @Override
    public void getWomanConclusion(VisitorModeWoman woman) {
        output.output(woman.getSex()+"成功时"+"背后多有一个不成功的男人");
    }
}
