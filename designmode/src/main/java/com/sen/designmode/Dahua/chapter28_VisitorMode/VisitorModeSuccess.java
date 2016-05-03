package com.sen.designmode.Dahua.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeSuccess extends VisitorModeAction {
    @Override
    public void getManConclusion(VisitorModeMan man) {
        output.output(man.getSex()+"成功时，多半背后有一个伟大的女人");
    }

    @Override
    public void getWomanConclusion(VisitorModeWoman woman) {
        output.output(woman.getSex()+"成功时，多半背后有一个失败的男人");
    }
}
