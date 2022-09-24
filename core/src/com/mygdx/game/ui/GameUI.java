package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.managers.AuctionManager;
import com.mygdx.game.managers.BuildingsManager;
import com.mygdx.game.managers.FieldManager;
import com.mygdx.game.board.Board;
import com.mygdx.game.logic.*;
import com.mygdx.game.managers.PayManager;
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

    private final FieldManager fieldManager;
    private AuctionManager auctionManager;
    private PayManager payManager;

    private boolean showPause;
    private final PauseMenuUI pauseMenuUI;
    private final BuildingsManager buildingsManager;

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

        this.buildingsManager = new BuildingsManager(game, mainController);
        this.fieldManager = new FieldManager(game, buildingsManager);
        this.auctionManager = null;
        this.pauseMenuUI = new PauseMenuUI(game);
        this.pauseMenuUI.initializeStage();


        linkControllers();
    }

    @Override
    public void draw(float delta) {
        super.draw(delta);
        mainGameUI.draw(delta);
        buildingsManager.process();
        fieldManager.process(delta);
        if (mainController.isFieldToSell()) {
            if (auctionManager == null) {
                auctionManager = new AuctionManager(game, board, buyController,
                        board.getField(game.getCurrentPlayer().getCurrentFieldNumber()));
            }
            auctionManager.process(delta);
        }
        if (mainController.isPlayerHaveToPay() && !mainController.isPayActionFinished()) {
            if (payManager == null)
                payManager = new PayManager(game, payController, board);
            payManager.process(delta);
        }
        checkKey();
        if (showPause) {
            pauseMenuUI.draw(delta);
            showPause = !pauseMenuUI.isClosed();
        }
        else
            pauseMenuUI.setClosed(false);
    }

    private void linkControllers() {
        mainController.linkSubjects(rollController);
        mainController.linkSubjects(moveController);
        mainController.linkSubjects(endTurnController);
        mainController.linkSubjects(fieldController);
        mainController.linkSubjects(buyController);
        mainController.linkSubjects(payController);
    }

    public boolean isTourFinished() {
        return mainController.isTourFinished();
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

    public void checkKey() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            showPause = true;
    }
}
