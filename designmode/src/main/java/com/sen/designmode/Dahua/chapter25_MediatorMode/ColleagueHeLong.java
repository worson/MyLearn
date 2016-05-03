package com.sen.designmode.Dahua.chapter25_MediatorMode;

/**
 * Created by secon on 2016/5/2.
 */
public class ColleagueHeLong extends Colleague {
    public ColleagueHeLong(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void send(String msg) {
        getMediator().send(msg,this);
    }

    @Override
    public void nofity(String msg) {
        output.output(msg);
    }
}
