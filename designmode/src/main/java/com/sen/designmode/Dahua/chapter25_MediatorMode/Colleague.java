package com.sen.designmode.Dahua.chapter25_MediatorMode;


import com.sen.designmode.Dahua.utils.ConsoleOutput;
import com.sen.designmode.Dahua.utils.IConsoleOutput;


/**
 * Created by secon on 2016/5/2.
 */
public abstract class Colleague {
    public IConsoleOutput output = ConsoleOutput.getInstance();
    private Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void send(String msg);
    public abstract void nofity(String msg);


}
