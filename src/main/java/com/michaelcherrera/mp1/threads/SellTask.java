package com.michaelcherrera.mp1.threads;

import com.michaelcherrera.mp1.actions.StockAction;
import com.michaelcherrera.mp1.models.Stockholder;
import com.michaelcherrera.mp1.models.Transaction;
import com.michaelcherrera.mp1.services.StockDAO;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides the {@link Runnable} operation to facilitate the continuous selling of stock.
 *
 * @author Michael C. Herrera
 */
public class SellTask implements Runnable, StopThreads {

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
     * Constructs a SellTask from the specified parameters.
     *
     * @param STOCK       the specific stock that will be bought.
     * @param STOCKHOLDER a stockholder who will buy shares.
     */
    public SellTask(StockAction STOCK, Stockholder STOCKHOLDER) {

        this.STOCK = STOCK;
        this.STOCKHOLDER = STOCKHOLDER;
        TRANSACTION = new Transaction();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        try {
            // While stop is false, continuously sell shares of the requested stock.
            while (!stop) {

                // Get the number of shares of the stock the stockholder owns.
                Long sharesOwned = STOCKHOLDER.getSharesOwned(STOCK.getName());
                // Randomly determine a number of shares to sell.
                int shares = (int) (Math.random() * sharesOwned + 1);

                // Get the current prices before attempting to sell shares. This is the requested price.
                TRANSACTION.setRequestedPrice(STOCK_DAO.currentPrice(STOCK.getName()));

                // If the stockholder owns shares, attempt to sell them. Otherwise, this transaction will be declined.
                if (sharesOwned > 0)
                    // Attempt to sell shares at the requested price.
                    TRANSACTION.setSuccess(STOCK.sell(shares, STOCKHOLDER, TRANSACTION));
                else
                    TRANSACTION.setSuccess(2);

                { // console output for UI comparison
                    if (TRANSACTION.getSuccess() == 0)
                        System.out.println(
                                "Success: " + STOCKHOLDER.getName() + " sold " + shares + " shares of " +
                                        STOCK.getName() + " at $" + TRANSACTION.getRequestedPrice());
                    else if (TRANSACTION.getSuccess() == 1)
                        System.out.println(
                                "Declined: " + STOCKHOLDER.getName() + "'s requested sell price of $" +
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
     * Stops the continuous selling of shares when true.
     *
     * @param stop true to stop the selling of shares
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