package com.mygdx.game.logic;


import java.util.ArrayList;

public class EndTurnController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;

    private boolean allowFinishTour;
    private boolean movingFinished;
    private boolean playerRolled;
    private boolean tourFinished;

    public EndTurnController() {
        this.observers = new ArrayList<>();

        this.allowFinishTour = false;
        this.movingFinished = false;
        this.playerRolled = false;
        this.tourFinished = false;
    }

    public boolean isAllowFinishTour() {
        return allowFinishTour;
    }

    public boolean isTourFinished() {
        return tourFinished;
    }

    public void setFinishTour(boolean finishTour) {
        this.tourFinished = finishTour;
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof MainController) {
            MainController mainController = (MainController) subject;
            this.playerRolled = mainController.isPlayerRolled();
            this.movingFinished = mainController.isMovingFinished();
        }
        this.allowFinishTour = this.playerRolled && this.movingFinished;
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }
}
