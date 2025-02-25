package com.michaelcherrera.mp1.actions;

import com.michaelcherrera.mp1.models.Stock;
import com.michaelcherrera.mp1.models.Stockholder;
import com.michaelcherrera.mp1.models.Transaction;
import com.michaelcherrera.mp1.services.DAO;
import com.michaelcherrera.mp1.services.StockDAO;
import com.michaelcherrera.mp1.services.TransactionsDAO;
import com.michaelcherrera.mp1.threads.BuyTask;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This abstract class contains the synchronized methods for buying and selling shares of stock. This class should be
 * extended by a singleton object for each stock to ensure that all stockholders are utilizing the same object and
 * methods to purchase a specific stock.
 *
 * @author Michael C. Herrera
 */
public abstract class StockAction {

    /**
     * Symbol (stock_id) of the selected stock.
     */
    public static String symbol;

    /**
     * Data Access Object for the stock table containing methods for manipulating stock data.
     */
    private final DAO<Stock> STOCK_DAO = new StockDAO();

    /**
     * Data Access Object for the transactions table containing methods for manipulating transaction data.
     */
    private final DAO<Transaction> TRANSACTION_DAO = new TransactionsDAO();

    /**
     * Returns the symbol of a specific stock.
     *
     * @return the symbol of a specific stock
     */
    public abstract String getName();

    /**
     * Attempts to purchase the requested number of shares of a stock at the requested price. If the current price does
     * not match the requested price, the transaction is declined. If there are not enough shares available, the
     * transaction is declined. This method must be shared by all stockholders attempting to purchase this stock and is
     * {@code synchronized} to ensure data integrity.
     *
     * @param shares      requested number of shares
     * @param stockholder the stockholder requesting the purchase
     * @param transaction a transaction
     * @return {@code 0} if the transaction was successful, {@code 1} if the transaction was declined as a result of the
     * current price, or {@code 2} if the transaction was declined due to insufficient shares
     */
    public synchronized int buy(int shares, Stockholder stockholder, Transaction transaction) {

        try {
            Stock stock = STOCK_DAO.read(symbol);

            // Confirm that the current stock prices matches the requested price. If they do not match, this
            // transaction is declined.
            if (!stock.getPriceCurrent().equals(transaction.getRequestedPrice()))
                return 1;
            if (!(stock.getNumberOfSharesAvailable() > shares))
                return 2;

            // Number of shares available is updated to reflect the transaction.
            stock.setNumberOfSharesAvailable(stock.getNumberOfSharesAvailable() - shares);
            STOCK_DAO.update(stock.getStockId(), stock);

            // Insert the buy transaction.
            transaction.setStockHolderID(stockholder.getStockholderID());
            transaction.setStockID(symbol);
            transaction.setQty(shares);
            TRANSACTION_DAO.create(transaction);
        } catch (SQLException e) {
            Logger.getLogger(BuyTask.class.getName()).log((Level.INFO), e.getMessage(), e);
        }
        return 0;
    }

    /**
     * Attempts to sell the requested number of shares of a stock at the requested price. If the current price does not
     * match the requested price, the transaction is declined. If there are not enough shares available, the transaction
     * is declined. This method must be shared by all stockholders attempting to purchase this stock and is
     * {@code synchronized} to ensure data integrity.
     *
     * @param shares      requested number of shares
     * @param stockholder the stockholder requesting the purchase
     * @param transaction a transaction
     * @return if the transaction was successful, {@code 1} if the transaction was declined as a result of the current
     * price, or {@code 2} if the transaction was declined due to insufficient shares
     */
    public synchronized int sell(int shares, Stockholder stockholder, Transaction transaction) {

        try {
            Stock stock = STOCK_DAO.read(symbol);

            // Confirm that the current stock prices matches the requested price. If they do not match, this
            // transaction is declined.
            if (!stock.getPriceCurrent().equals(transaction.getRequestedPrice()))
                return 1;
            if (!(stock.getNumberOfSharesAvailable() > shares))
                return 2;

            // Number of shares sold is updated to reflect the transaction.
            stock.setNumberOfSharesSold(stock.getNumberOfSharesSold() + shares);
            STOCK_DAO.update(stock.getStockId(), stock);

            // Insert the sell transaction.
            transaction.setStockHolderID(stockholder.getStockholderID());
            transaction.setStockID(symbol);
            transaction.setQty(-shares);
            TRANSACTION_DAO.create(transaction);
        } catch (SQLException e) {
            Logger.getLogger(BuyTask.class.getName()).log((Level.INFO), e.getMessage(), e);
        }
        return 0;
    }
}