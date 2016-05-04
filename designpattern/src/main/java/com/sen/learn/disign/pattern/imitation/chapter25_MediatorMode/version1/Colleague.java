package com.sen.learn.disign.pattern.imitation.chapter25_MediatorMode.version1;

import com.sen.learn.disign.pattern.imitation.utils.ConsoleOutput;
import com.sen.learn.disign.pattern.imitation.utils.IConsoleOutput;

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
