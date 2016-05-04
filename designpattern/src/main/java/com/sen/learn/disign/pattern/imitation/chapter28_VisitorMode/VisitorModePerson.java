package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

/**
 * Created by secon on 2016/5/2.
 * 为每�?个类声明�?个vistor操作
 */
public abstract class VisitorModePerson {
    public String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
//    * 为每�?个类声明�?个vistor操作
    public abstract void accept(VisitorModeAction visitor);
}
