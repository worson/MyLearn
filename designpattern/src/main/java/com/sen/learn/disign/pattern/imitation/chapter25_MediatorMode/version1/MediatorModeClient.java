package com.sen.learn.disign.pattern.imitation.chapter25_MediatorMode.version1;

/**
 * Created by secon on 2016/5/2.
 * 完成功能：AB之间要发送信息，需要通过C，在C之间支持AB数据传输，A设置传输的目的为B
 */
public class MediatorModeClient {
    public static void main(String[] args) {
        ConcreteMediator concreteMediator = new ConcreteMediator();
        //配置管理类
        ColleagueZhangRui zhangRui = new ColleagueZhangRui(concreteMediator);
        ColleagueHeLong heLong = new ColleagueHeLong(concreteMediator);

        //在管理类中设定监听者
        concreteMediator.setHeLong(heLong);
        concreteMediator.setZhangRui(zhangRui);

        heLong.send("You also dare to say that you are a genius programmer ");//你还敢说你是天才程序员
        zhangRui.send("All the time ");//一直都是



    }
}
