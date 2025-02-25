package com.michaelcherrera.mp1.controllers;

import com.michaelcherrera.mp1.actions.StockAction;
import com.michaelcherrera.mp1.models.Stock;
import com.michaelcherrera.mp1.models.Stockholder;
import com.michaelcherrera.mp1.services.StockDAO;
import com.michaelcherrera.mp1.services.StockholderDAO;
import com.michaelcherrera.mp1.threads.BuyTask;
import com.michaelcherrera.mp1.threads.SellTask;
import com.michaelcherrera.mp1.threads.StopThreads;
import com.michaelcherrera.mp1.util.Utilities;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.michaelcherrera.mp1.util.Utilities.findComponent;

/**
 * This Beans handles the server-side UI functionality of the of index.xhtml
 */
@Named(value = "stockBean")
@ViewScoped
public class StockBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(StockBean.class.getName());

    /**
     * List of existing stockholders.
     */
    private List<Stockholder> stockholders;

    /**
     * List to contain threads.
     */
    private final List<Thread> THREADS = new ArrayList<>();

    /**
     * List to contain the thread tasks.
     */
    private final List<StopThreads> TASKS = new ArrayList<>();

    /**
     * Stock Data Access Object containing methods to manipulate the stock table.
     */
    private final StockDAO STOCK_DAO;

    /**
     * Stockholder Data Access Object containing methods to manipulate the stockholder table.
     */
    private final StockholderDAO STOCKHOLDER_DAO;

    private Stock stock;  // currently selected stock
    private String stockId;  // stock_id of currently selected stock

    /**
     * Constructs the StockBean
     */
    public StockBean() {

        STOCK_DAO = new StockDAO();
        STOCKHOLDER_DAO = new StockholderDAO();
        stock = new Stock();

        try {
            // default stock onload
            stockId = "AMC";
            setStock();

            stockholders = STOCKHOLDER_DAO.readAll();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        for (int i = 0; i < stockholders.size(); i++) {
            THREADS.add(null);
            TASKS.add(null);
        }
    }

    /**
     * Get the value of stock.
     *
     * @return the value of stock
     */
    public Stock getStock() {return stock;}

    /**
     * Set the value of stock.
     */
    public void setStock() {

        stopThreads();
        try {
            // If the stock did not change, no update is necessary.
            if (!Objects.equals(this.stock.getStockId(), this.stockId))
                updateStockInfo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Get the value of stockholders.
     *
     * @return the value of stockholders
     */
    public List<Stockholder> getStockholders() {return stockholders;}

    /**
     * Get the value of stockId.
     *
     * @return the value of stockId
     */
    public String getStockId() {return stockId;}

    /**
     * Set the value of stockId.
     *
     * @param stockId new value of stockId
     */
    public void setStockId(String stockId) {this.stockId = stockId;}

    /**
     * Get the value of tasks.
     *
     * @return the value of tasks
     */
    public List<StopThreads> getTASKS() {return TASKS;}

    /**
     * Updates the displayed Stock data.
     */
    public boolean updateStockInfo() {

        try {
            this.stock = STOCK_DAO.read(stockId);
            PrimeFaces.current().ajax().update(findComponent("stock_info"));
            updateTransactions();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    /**
     * Updates the stockholder transaction data.
     */
    public void updateTransactions() {

        for (int i = 0; i < TASKS.size(); i++) {

            try {
                // If the thread for this stockholder is not null, and the stockholder does not own any of the
                // current stock, stop the sell task.
                if (THREADS.get(i) != null && stockholders.get(i).getSharesOwned(stockId) == 0)
                    stopSell(stockholders.get(i).getStockholderID());

                // If the thread for this stockholder is not null, and there is no shares available, stop the sell task.
                if (TASKS.get(i) != null && stock.getNumberOfSharesAvailable() == 0)
                    stopSell(stockholders.get(i).getStockholderID());

                // If a transaction is declined, show a message.
                if (TASKS.get(i) != null && TASKS.get(i).getTRANSACTION().getSuccess() != 0)
                    Utilities.addMessage(FacesMessage.SEVERITY_WARN, "Transaction Declined.",
                            "A tranaction for " + stockholders.get(i).getName() + " was not successful.");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

            PrimeFaces.current().ajax().update(findComponent("stock-holder-" + (i + 1)));
        }

    }

    /**
     * Returns the {@link StockAction} object necessary for the purchasing or selling of shares.
     *
     * @param stockholderId ID of this stockholder
     * @return the {@link StockAction} object necessary for the purchasing or selling of shares.
     */
    private StockAction startTask(int stockholderId) {

        // Stop any already running tasks, if startBuy/startCell is called.
        stopBuy(stockholderId);
        stopSell(stockholderId);

        StockAction stock = null;

        try {
            // Get the class object for the selected stock.
            Class<?> clazz = Class.forName("com.michaelcherrera.mp1.actions." + stockId);

            // Get the instance of the selected stock and assign it to stock;
            Method method = clazz.getMethod("getStockAction");
            stock = (StockAction) method.invoke(null);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return stock;
    }

    /**
     * Starts buying the selected stock for this stockholder. Between 1 and 10 shares are purchased at a time at
     * intervals between purchases until {@link #stopBuy(int)} is called or until no more shares are available.
     *
     * @param stockholderID ID if this stockholder
     * @see BuyTask
     */
    public void startBuy(int stockholderID) {

        // If no stock is selected, return.
        if (stockId == null) {return;}

        try {
            // Retrieve the data for the specific stockholder from the database.
            Stockholder stockholder = STOCKHOLDER_DAO.read(stockholderID);

            // Create a new buyTask for the selected stock and stockholder and add the task to tasks list.
            BuyTask buyTask = new BuyTask(startTask(stockholderID), stockholder);
            TASKS.set(stockholderID - 1, buyTask);
            THREADS.set(stockholderID - 1, new Thread(buyTask));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        THREADS.get(stockholderID - 1).start();
    }

    /**
     * Stops the {@link BuyTask} for this stockholder.
     *
     * @param stockHolderID ID of this stockholder.
     */
    public void stopBuy(int stockHolderID) {
        // Stop the task if the currently running task is a BuyTask
        if (TASKS.get(stockHolderID - 1) instanceof BuyTask) {
            TASKS.get(stockHolderID - 1).stopThreads(true);
            THREADS.set(stockHolderID - 1, null);
            System.out.println("Task stopped");
        }
    }

    /**
     * Starts selling the selected stock for this stockholder. Shares are sold at intervals between sells until
     * {@link #stopSell(int)} is called or until no more shares are available.
     *
     * @param stockholderID ID of this stockholder
     * @see SellTask
     */
    public void startSell(int stockholderID) {
        // If no stock is selected, return.
        if (stockId == null) return;

        try {
            Stockholder stockHolder = STOCKHOLDER_DAO.read(stockholderID);
            SellTask sellTask = new SellTask(startTask(stockholderID), stockHolder);
            TASKS.set(stockholderID - 1, sellTask);
            THREADS.set(stockholderID - 1, new Thread(sellTask));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        THREADS.get(stockholderID - 1).start();
    }

    /**
     * Stops the {@link SellTask} for this stockholder.
     *
     * @param stockHolderID ID of this stockholder.
     */
    public void stopSell(int stockHolderID) {

        if (TASKS.get(stockHolderID - 1) instanceof SellTask) {
            TASKS.get(stockHolderID - 1).stopThreads(true);
            THREADS.set(stockHolderID - 1, null);
            System.out.println("Task stopped");
        }
    }

    /**
     * Stops all running buy/sell tasks.
     */
    public void stopThreads() {

        for (int i = 0; i < TASKS.size(); i++) {
            if (TASKS.get(i) != null) {
                TASKS.get(i).stopThreads(true);
                THREADS.set(i, null);

            }
        }
    }
}