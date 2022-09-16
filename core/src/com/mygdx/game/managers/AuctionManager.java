package com.mygdx.game.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.BuyController;
import com.mygdx.game.ui.AuctionUI;
import com.mygdx.game.ui.BaseUI;
import com.mygdx.game.ui.BuyUI;

import java.util.Objects;

public class AuctionManager {
    private final Eurobusiness game;
    private final Board board;
    private final BuyController buyController;
    private final Field field;

    private AuctionUI auctionUI;
    private BuyUI buyUI;
    private BaseUI currentUI;

    public AuctionManager(Eurobusiness game, Board board, BuyController buyController, Field field) {
        this.game = Objects.requireNonNull(game, "game is null");
        this.board = Objects.requireNonNull(board, "board is null");
        this.buyController = Objects.requireNonNull(buyController, "buyController is null");
        this.field = Objects.requireNonNull(field, "field is null");

        this.buyController.createBuyAction();
        this.buyController.createAuctionAction(board, game.players, game.getCurrentPlayer());
        this.buyController.setFieldAndPlayer(field, game.getCurrentPlayer());

        this.auctionUI = null;
    }

    public void process(float delta) {
        buyController.process();
        if (buyUI == null) {
            buyUI = new BuyUI(game, field, buyController);
            buyUI.initializeStage();
            currentUI = buyUI;
        }
        if (!buyController.isBuyActionFinished()) {
            buyUI.draw(delta);
        }
        else if (buyController.isAuctionStarted() && !buyController.isAuctionFinished()) {
            if (auctionUI == null) {
                auctionUI = new AuctionUI(game, board, field, buyController);
                auctionUI.initializeStage();
            }
            auctionUI.draw(delta);
        }

    }
}
