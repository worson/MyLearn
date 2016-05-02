package com.sen.learn.disign.pattern.imitation.chapter25_MediatorMode.version1;

/**
 * Created by secon on 2016/5/2.
 */
public class ColleagueZhangRui extends Colleague {
    public ColleagueZhangRui(Mediator mediator) {
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
