package com.sen.designmode.Dahua.chapter25_MediatorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class ConcreteMediator extends Mediator{
    private ColleagueZhangRui zhangRui;
    private ColleagueHeLong heLong;
    @Override
    public void send(String msg, Colleague colleague) {
        if (colleague instanceof ColleagueZhangRui){
            heLong.nofity(msg);
        }if (colleague instanceof ColleagueHeLong){
            zhangRui.nofity(msg);
        }
    }

    public void setZhangRui(ColleagueZhangRui zhangRui) {
        this.zhangRui = zhangRui;
    }

    public void setHeLong(ColleagueHeLong heLong) {
        this.heLong = heLong;
    }
}
