package com.sen.learn.disign.pattern.imitation.chapter28;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeMan extends VisitorModePerson {

    public VisitorModeMan() {
        setSex("男人");
    }

    @Override
    public void accept(VisitorModeAction visitor) {
        visitor.getManConclusion(this);
    }
}
