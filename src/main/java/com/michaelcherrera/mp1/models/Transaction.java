package com.michaelcherrera.mp1.models;

/**
 * The Transaction class contains members and methods pertaining to a specific purchase or sell transaction.
 *
 * @author Michael C. Herrera
 */
public class Transaction {

    private Integer transactionId;
    private Integer stockHolderID;
    private String stockID;
    private Integer qty;
    private int success;
    private Double requestedPrice;

    /**
     * Constructs a Transaction.
     */
    public Transaction() {}

    /**
     * Constructs a Transaction from the specified parameters.
     *
     * @param transactionId ID of the transaction
     * @param stockHolderID ID of the stockholder to whom this transaction pertains
     * @param stockID       symbol of the stock that is bought or sold in this transaction
     * @param qty           the quantity of shares bought or sold in this transaction
     */
    public Transaction(int transactionId, int stockHolderID, String stockID, int qty) {

        this.transactionId = transactionId; this.stockHolderID = stockHolderID; this.stockID = stockID; this.qty = qty;
    }

    /**
     * Constructs a Transaction from another Transaction.
     *
     * @param transaction a Transaction
     */
    public Transaction(Transaction transaction) {

        this(transaction.transactionId, transaction.stockHolderID, transaction.stockID, transaction.qty);
    }

    /**
     * Get the value of transactionId.
     *
     * @return the value of transactionId
     */
    public Integer getTransactionId() {return transactionId;}

    /**
     * Set the value of transactionId.
     *
     * @param transactionId new value of transactionId
     */
    public void setTransactionId(Integer transactionId) {this.transactionId = transactionId;}

    /**
     * Get the value of stockHolderID.
     *
     * @return the value of stockHolderID
     */
    public Integer getStockHolderID() {return stockHolderID;}

    /**
     * Set the value of stockHolderID.
     *
     * @param stockHolderID new value of stockHolderID
     */
    public void setStockHolderID(Integer stockHolderID) {this.stockHolderID = stockHolderID;}

    /**
     * Get the value of stockID.
     *
     * @return the value of stockID
     */
    public String getStockID() {return stockID;}

    /**
     * Set the value of stockID.
     *
     * @param stockID new value of stockID
     */
    public void setStockID(String stockID) {this.stockID = stockID;}

    /**
     * Get the value of qty.
     *
     * @return the value of qty
     */
    public Integer getQty() {return qty;}

    /**
     * Set the value of qty.
     *
     * @param qty new value of qty
     */
    public void setQty(Integer qty) {this.qty = qty;}

    /**
     * Get the value of requestedPrice.
     *
     * @return the value of requestedPrice
     */
    public Double getRequestedPrice() {return requestedPrice;}

    /**
     * Set the value of requestedPrice.
     *
     * @param requestedPrice new value of requestedPrice
     */
    public void setRequestedPrice(Double requestedPrice) {this.requestedPrice = requestedPrice;}

    /**
     * Get the value of success.
     *
     * @return the value of success
     */
    public int getSuccess() {return success;}

    /**
     * Set the value of success.
     *
     * @param success new value of success
     */
    public void setSuccess(int success) {this.success = success;}

    /**
     * Get the status of the last transaction.
     *
     * @return the status of the last transaction
     */
    public String getTransactionStatus(String stock) {

        if (stock.equals(this.stockID))
            return success == 0 ? "Transaction successful" : "Transaction declined";
        return null;
    }

    /**
     * Returns a String description of the most recent transaction for the specified stockholder and stock.
     *
     * @param stockholder a stockholder name
     * @param stock       a stock symbol
     * @return a String description of the most recent transaction for the specified stockholder and stock
     */
    public String getTransactionMessage(String stockholder, String stock) {

        if (stock.equals(this.stockID))
            if (success == 0)
                if (qty > 0)
                    return "Succes: " + stockholder + " bought " + qty + " shares of " + stock + " at $" +
                            String.format("%.2f", requestedPrice);
                else
                    return "Succes: " + stockholder + " sold " + -qty + " shares of " + stock + " at $" +
                            String.format("%.2f", requestedPrice);
            else if (success == 1)
                return "Declined: " + stockholder + "'s requested price of $" + String.format("%.2f", requestedPrice) +
                        " was declined.";
            else
                return "Declined: Not enough shares of " + stock + " are available.";
        return "";
    }
}
