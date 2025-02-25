package com.michaelcherrera.mp1.threads;

import com.michaelcherrera.mp1.actions.StockAction;
import com.michaelcherrera.mp1.models.Stockholder;
import com.michaelcherrera.mp1.models.Transaction;
import com.michaelcherrera.mp1.services.StockDAO;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides the {@link Runnable} operation to facilitate the continuous purchase of stock.
 *
 * @author Michael C. Herrera
 */
public class BuyTask implements Runnable, StopThreads {

    /**
     * Data Access Object for the stock table that contains methods for manipulation of stock data.
     */
    private final StockDAO STOCK_DAO = new StockDAO();

    /**
     * A specific stock that will be bought.
     */
    private final StockAction STOCK;

    /**
     * A stockholder who will buy shares.
     */
    private final Stockholder STOCKHOLDER;

    /**
     * A transaction that will contain the data related to this buy task.
     */
    private final Transaction TRANSACTION;

    /**
     * Allows the task to complete when true.
     */
    boolean stop = false;

    /**
     * Constructs a BuyTask from the specified parameters.
     *
     * @param STOCK       the specific stock that will be bought.
     * @param STOCKHOLDER a stockholder who will buy shares.
     */
    public BuyTask(StockAction STOCK, Stockholder STOCKHOLDER) {

        this.STOCK = STOCK;
        this.STOCKHOLDER = STOCKHOLDER;
        this.TRANSACTION = new Transaction();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        try {
            // While stop is false, continuously purchase shares of the requested stock.
            while (!stop) {

                // Random between 1 and 10 to be the number of shares to purchase.
                int shares = (int) (Math.random() * 10 + 1);

                // Get the current prices before attempting to purchase shares. This is the requested price.
                TRANSACTION.setRequestedPrice(STOCK_DAO.currentPrice(STOCK.getName()));

                // Attempt to purchase shares at the requested price.
                TRANSACTION.setSuccess(STOCK.buy(shares, STOCKHOLDER, TRANSACTION));

                { // console output for UI comparison
                    if (TRANSACTION.getSuccess() == 0)
                        System.out.println("Success: " + STOCKHOLDER.getName() + " bought " + shares + " shares of " +
                                STOCK.getName() + " at $" + TRANSACTION.getRequestedPrice());
                    else if (TRANSACTION.getSuccess() == 1)
                        System.out.println(
                                "Declined: " + STOCKHOLDER.getName() + "'s requested buy price of $" +
                                        TRANSACTION.getRequestedPrice() + " was not successful.");
                    else if (TRANSACTION.getSuccess() == 2)
                        System.out.println("Declined: Not enough shares of " + STOCK.getName() + " are available.");
                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException | SQLException e) {
            Logger.getLogger(BuyTask.class.getName()).log((Level.INFO), e.getMessage(), e);
        }
    }

    /**
     * Stops the continuous buying of shares when true.
     *
     * @param stop true to stop the buying of shares
     */
    @Override
    public void stopThreads(boolean stop) {this.stop = stop;}

    /**
     * Returns the last transaction created by this continuous task.
     *
     * @return the last transaction created by this continuous task.
     */
    public Transaction getTRANSACTION() {return TRANSACTION;}
}