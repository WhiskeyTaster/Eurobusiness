package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.action.Action;
import com.mygdx.game.owners.Player;

import java.util.ArrayList;
import java.util.Iterator;


public class MoveController implements LinkedSubject{
    private final ArrayList<LinkedSubject> observers;
    private Action action;

    private int rolledValue;

    private boolean goToPrison;
    private boolean movingFinished;
    private boolean playerRolled;
    private boolean round;

    public MoveController() {
        this.observers = new ArrayList<>();

        this.rolledValue = 0;

        this.goToPrison = false;
        this.movingFinished = false;
        this.playerRolled = false;
        this.round = false;
    }

    public void process() {
        action.process();
    }

    public boolean isGoToPrison() {
        return goToPrison;
    }

    public boolean isMovingFinished() {
        return movingFinished;
    }

    public boolean isPlayerRolled() {
        return playerRolled;
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public void createMoveAction(Board board, Eurobusiness game) {
        this.action = new MoveAction(board, game);
    }

    @Override
    public void update(LinkedSubject subject) {
        if (subject instanceof MainController) {
            this.playerRolled = ((MainController) subject).isPlayerRolled();
            if (this.playerRolled) {
                this.goToPrison = ((MainController) subject).isTwoPairsRolled();
                this.rolledValue = ((MainController) subject).getRolledValue();
            }
        }
    }

    public boolean isRound() {
        return round;
    }

    @Override
    public ArrayList<LinkedSubject> getSubjects() {
        return observers;
    }

    class MoveAction implements Action{
        private final Eurobusiness game;
        private final Player movingPlayer;
        private final Board board;

        private int nextFieldNumber;

        private boolean movingStarted;
        private boolean movingFinished;

        private final ArrayList<Integer> path;
        private Iterator<Integer> iterator;

        private int startingField;
        private int endingField;

        public MoveAction(Board board, Eurobusiness game) {
            this.board = board;
            this.game = game;
            this.movingPlayer = game.getCurrentPlayer();

            this.nextFieldNumber = 0;

            this.movingStarted = false;
            this.movingFinished = false;

            this.path = new ArrayList<>();
            this.iterator = new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Integer next() {
                    return null;
                }
            };
            this.startingField = this.movingPlayer.getCurrentFieldNumber();
            this.endingField = startingField;
        }

        public void process() {
            if (isPlayerRolled() && !isMovingFinished()) {
                if (!isMovingStarted())
                    calculateAndSetUp();
                else
                    movePlayer();
            } else {
                informController();
            }
        }

        @Override
        public void informController() {
            MoveController.this.movingFinished = this.movingFinished;
            MoveController.this.round = startingField > endingField;
            MoveController.this.informSubjects();
        }

        public boolean isMovingFinished() {
            return movingFinished;
        }

        public void moveToPrison() {
            Field prison = board.getField(Board.PRISON_FIELD_NUMBER);
            float x = prison.getX() + movingPlayer.getPawn().getWidth() / 2;
            float y = prison.getY() + movingPlayer.getPawn().getHeight() / 2;
            movingPlayer.move(-Math.abs(movingPlayer.getPawn().getX() - x),
                    -Math.abs(movingPlayer.getPawn().getY() - y));
            movingPlayer.setCurrentFieldNumber(Board.PRISON_FIELD_NUMBER);
            movingFinished = true;
        }

        public void movePlayer() {
            final int SPEED = 400;
            Field field = board.getField(nextFieldNumber);
            float x = field.getX() + field.getWidth() / 2 - movingPlayer.getPawn().getWidth() / 2;
            float y = field.getY() + field.getHeight() / 2 - movingPlayer.getPawn().getHeight() / 2;
            if (movingPlayer.getPawn().getX() != x) {
                if (movingPlayer.getPawn().getX() < x) {
                    movingPlayer.move(SPEED * Gdx.graphics.getDeltaTime(), 0);
                    if (movingPlayer.getPawn().getX() >= x)
                        movingPlayer.getPawn().setPosition(x, y);
                }
                else if (movingPlayer.getPawn().getX() > x) {
                    movingPlayer.move(-SPEED * Gdx.graphics.getDeltaTime(), 0);
                    if (movingPlayer.getPawn().getX() <= x)
                        movingPlayer.getPawn().setPosition(x, y);
                }
            }
            else if (movingPlayer.getPawn().getY() != y) {
                if (movingPlayer.getPawn().getY() < y) {
                    movingPlayer.move(0, SPEED * Gdx.graphics.getDeltaTime());
                    if (movingPlayer.getPawn().getY() >= y)
                        movingPlayer.getPawn().setPosition(x, y);
                }
                else if (movingPlayer.getPawn().getY() > y) {
                    movingPlayer.move(0, -SPEED * Gdx.graphics.getDeltaTime());
                    if (movingPlayer.getPawn().getY() <= y)
                        movingPlayer.getPawn().setPosition(x, y);
                }
            }
            else {
                if (nextFieldNumber == 1)
                    game.bank.pay(game.getCurrentPlayer(), 400);
                if (iterator.hasNext())
                    nextFieldNumber = iterator.next();
                else {
                    movingPlayer.setCurrentFieldNumber(nextFieldNumber);
                    movingFinished = true;
                }
            }
        }

        private boolean isMovingStarted() {
            return movingStarted;
        }

        private void calculateAndSetUp() {
            movingStarted = true;
            if (isGoToPrison()) {
                moveToPrison();
            }
            else {
                calculatePath();
                setUpNewPath();
            }
        }

        private void calculatePath() {
            int rolledValue = getRolledValue();
            int currentFieldNumber = movingPlayer.getCurrentFieldNumber();
            int targetField = currentFieldNumber + rolledValue >= 41 ?
                    (currentFieldNumber + rolledValue) % 41 + 1 : currentFieldNumber + rolledValue;

            if (targetField < currentFieldNumber) {
                for (int i = currentFieldNumber + 1; i < 41; i++)
                    path.add(i);
                for (int i = Board.START_FIELD_NUMBER; i <= targetField; i++)
                    path.add(i);
            } else {
                for (int i = currentFieldNumber + 1; i <= targetField; i++)
                    path.add(i);
            }
            endingField = targetField;
        }

        private void setUpNewPath() {
            iterator = path.iterator();
            nextFieldNumber = iterator.next();
        }
    }
}