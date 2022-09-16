package com.mygdx.game.managers;

import com.mygdx.game.Eurobusiness;
import com.mygdx.game.board.Board;
import com.mygdx.game.board.Field;
import com.mygdx.game.logic.BuyController;
import com.mygdx.game.owners.Player;
import com.mygdx.game.ui.AuctionUI;
import com.mygdx.game.ui.BuyUI;
import com.mygdx.game.ui.FieldBoughtUI;

import java.util.Objects;

public class AuctionManager {
    private final Eurobusiness game;
    private final Board board;
    private final BuyController buyController;
    private final Field field;

    private AuctionUI auctionUI;
    private BuyUI buyUI;
    private final FieldBoughtUI fieldBoughtUI;

    public AuctionManager(Eurobusiness game, Board board, BuyController buyController, Field field) {
        this.game = Objects.requireNonNull(game, "game is null");
        this.board = Objects.requireNonNull(board, "board is null");
        this.buyController = Objects.requireNonNull(buyController, "buyController is null");
        this.field = Objects.requireNonNull(field, "field is null");

        this.buyController.createBuyAction();
        this.buyController.createAuctionAction(board, game.players, game.getCurrentPlayer());
        this.buyController.setFieldAndPlayer(field, game.getCurrentPlayer());

        this.auctionUI = null;
        this.fieldBoughtUI = new FieldBoughtUI(game, buyController);
    }

    public void process(float delta) {
        buyController.process();
        if (buyUI == null) {
            buyUI = new BuyUI(game, field, buyController);
            buyUI.initializeStage();
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
        if (field.getOwner() instanceof Player && !fieldBoughtUI.isClosed()) {
            if (!fieldBoughtUI.haveNewOwner()) {
                if (buyController.isAuctionFinished())
                    fieldBoughtUI.setNewOwner(buyController.getCurrentAuctioneer());
                else
                    fieldBoughtUI.setNewOwner(game.getCurrentPlayer());
                fieldBoughtUI.initializeStage();
            }
            fieldBoughtUI.draw(delta);
        }
    }
}
