package com.michaelcherrera.mp1.models;

import com.michaelcherrera.mp1.services.TransactionsDAO;

import java.sql.SQLException;

/**
 * The Stockholder class contains members and methods pertaining to a specific stockholder.
 *
 * @author Michael C. Herrera
 */
public class Stockholder {

    private Integer stockholderID;
    private String name;

    /**
     * Constructs a Stockholder.
     */
    public Stockholder() {}

    /**
     * Constructs a Stockholder with specified parameters.
     *
     * @param stockholderID stockholder's ID
     * @param name          stockholder's name
     */
    public Stockholder(Integer stockholderID, String name) {

        this.stockholderID = stockholderID; this.name = name;
    }

    /**
     * Constructs a Stockholder from another Stockholder.
     *
     * @param stockHolder a Stockholder
     */
    public Stockholder(Stockholder stockHolder) {

        this(stockHolder.stockholderID, stockHolder.name);
    }

    /**
     * Get the value of stockHolderId.
     *
     * @return the value of stockHolderId
     */
    public Integer getStockholderID() {return stockholderID;}

    /**
     * Set the value of stockHolderId.
     *
     * @param stockholderID new value of stockHolderId
     */
    public void setStockholderID(Integer stockholderID) {this.stockholderID = stockholderID;}

    /**
     * Get the value of name.
     *
     * @return the value of name
     */
    public String getName() {return name;}

    /**
     * Set the value of name.
     *
     * @param name new value of name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Get the value of sharesOwned.
     *
     * @return the value of sharesOwned
     */
    public Long getSharesOwned(String stockID) throws SQLException {

        return new TransactionsDAO().shareTotal(stockID, stockholderID);
    }

    /**
     * Get the value of lastTransaction.
     *
     * @return the value of lastTransaction
     */
    public Transaction getLastTransactions(String stockID) throws SQLException {

        return new TransactionsDAO().getLastTransaction(stockID, stockholderID);
    }
}

