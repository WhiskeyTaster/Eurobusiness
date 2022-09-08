package com.mygdx.game.logic;


import java.util.ArrayList;


public interface LinkedSubject {
    void update(LinkedSubject subject);
    ArrayList<LinkedSubject> getSubjects();

    default void linkSubjects(LinkedSubject linkedSubject) {
        this.add(linkedSubject);
        linkedSubject.add(this);
    }

    default void informSubjects() {
        for (LinkedSubject subject : this.getSubjects()) {
            subject.update(this);
        }
    }

    default void add(LinkedSubject subject) {
        this.getSubjects().add(subject);
    }
}
