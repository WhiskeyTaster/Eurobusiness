package com.mygdx.game.managers;

import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.PayController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.ui.OutOfMoneyUI;
import com.mygdx.game.ui.PayUI;

import java.util.Objects;

public class PayManager {
    private final Eurobusiness game;
    private final PayController payController;
    private final Board board;
    private final Player player;
    private final Field field;

    private final PayUI payUI;
    private final OutOfMoneyUI outOfMoneyUI;
    private boolean initialized;

    public PayManager(Eurobusiness game, PayController payController, Board board) {
        this.game = Objects.requireNonNull(game, "game is null");
        this.payController = Objects.requireNonNull(payController, "payController is null");
        this.board = Objects.requireNonNull(board, "board is null");
        this.player = Objects.requireNonNull(game.getCurrentPlayer(), "player is null");
        this.field = Objects.requireNonNull(board.getField(this.player.getCurrentFieldNumber()), "field is null");
        this.payUI = new PayUI(game, field, payController);
        this.outOfMoneyUI = new OutOfMoneyUI(game, payController);

        payController.createPayAction(field, player);
        this.initialized = false;
    }

    public void process(float delta) {
        payController.process();
        if (!initialized) {
            payUI.initializeStage();
            outOfMoneyUI.initializeStage();
            initialized = true;
        }
        if (payUI.isOutOfMoney())
            outOfMoneyUI.draw(delta);
        if (!payUI.isClosed())
            payUI.draw(delta);

    }

}
