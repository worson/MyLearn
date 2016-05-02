package com.sen.learn.disign.pattern.imitation.chapter28;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeWoman extends VisitorModePerson {

    public VisitorModeWoman() {
        setSex("女人");
    }

    @Override
    public void accept(VisitorModeAction visitor) {
        visitor.getWomanConclusion(this);
    }
}
