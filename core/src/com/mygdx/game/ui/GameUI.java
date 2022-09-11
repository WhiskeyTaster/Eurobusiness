package com.mygdx.game.ui;

import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.logic.*;
import org.jetbrains.annotations.NotNull;

public class GameUI extends BaseUI{

    private final Board board;

    private final MainController mainController;
    private final RollController rollController;
    private final MoveController moveController;

    private final EndTurnController endTurnController;
    private final FieldController fieldController;
    private final BuyController buyController;
    private final PayController payController;

    private final MainGameUI mainGameUI;

    public GameUI(final @NotNull Eurobusiness game, final @NotNull Board board) {
        super(game);
        this.board = board;

        this.mainController = new MainController();
        this.rollController = new RollController();
        this.moveController = new MoveController();
        this.endTurnController = new EndTurnController();
        this.fieldController = new FieldController();
        this.buyController = new BuyController();
        this.payController = new PayController();

        this.mainGameUI = new MainGameUI(game, board, endTurnController, fieldController, moveController, rollController);
        this.mainGameUI.initializeStage();

        linkControllers();
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        mainGameUI.draw(delta);
    }

    private void linkControllers() {
        mainController.linkSubjects(rollController);
        mainController.linkSubjects(moveController);
        mainController.linkSubjects(endTurnController);
        mainController.linkSubjects(fieldController);
        mainController.linkSubjects(buyController);
        mainController.linkSubjects(payController);
    }

    @Override
    void drawShapeRenderer() {

    }

    @Override
    void drawBatch() {

    }

    @Override
    void initializeLabels() {

    }

    @Override
    void initializeButtons() {

    }

    @Override
    void initializeTextFields() {

    }
}
