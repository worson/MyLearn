package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 * Ϊÿһ��������һ��vistor����
 */
public abstract class VisitorModePerson {
    public String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
//    * Ϊÿһ��������һ��vistor����
    public abstract void accept(VisitorModeAction visitor);
}
