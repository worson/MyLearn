package com.sen.learn.disign.pattern.imitation.chapter28_VisitorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by secon on 2016/5/2.
 */
public class VisitorModeManager {
    private List<VisitorModePerson> elements = new ArrayList<VisitorModePerson>();
    public void attach(VisitorModePerson person){
        elements.add(person);
    }
    public void deattach(VisitorModePerson person){
        elements.remove(person);
    }
    public void display(VisitorModeAction action){
        for (VisitorModePerson person:elements) {
            person.accept(action);
        }
    }
}
