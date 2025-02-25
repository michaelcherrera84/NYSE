package com.michaelcherrera.mp1.models;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * The Stock class contains members and objects related to specific stocks.
 *
 * @author Michael C. Herrera
 */
public class Stock {

    private String stockId;
    private String companyName;
    private Double priceCurrent;
    private Double priceClosing;
    private Long numberOfSharesAvailable;
    private Long numberOfSharesSold;

    /**
     * Constructs a Stock.
     */
    public Stock() {}

    /**
     * Constructs a Stock
     *
     * @param stockId                 stock symbol
     * @param companyName             name of the company
     * @param priceCurrent            current price of a share
     * @param priceClosing            previous day's price of a share at market close
     * @param numberOfSharesAvailable number of shares available to be sold
     * @param numberOfSharesSold      number of shares that have been sold
     */
    public Stock(String stockId, String companyName, Double priceCurrent, Double priceClosing,
                 Long numberOfSharesAvailable, Long numberOfSharesSold) {

        this.stockId = stockId; this.companyName = companyName; this.priceCurrent = priceCurrent;
        this.priceClosing = priceClosing; this.numberOfSharesAvailable = numberOfSharesAvailable;
        this.numberOfSharesSold = numberOfSharesSold;
    }

    /**
     * Constucts a Stock from a Stock.
     *
     * @param stock a Stock
     */
    public Stock(Stock stock) {

        this(stock.stockId, stock.companyName, stock.priceCurrent, stock.priceClosing, stock.numberOfSharesAvailable,
                stock.numberOfSharesSold);
    }

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
     * Get the value of companyName.
     *
     * @return the value of companyName
     */
    public String getCompanyName() {return companyName;}

    /**
     * Set the value of companyName.
     *
     * @param companyName new value of companyName
     */
    public void setCompanyName(String companyName) {this.companyName = companyName;}

    /**
     * Get the value of priceCurrent.
     *
     * @return the value of priceCurrent
     */
    public Double getPriceCurrent() {return priceCurrent;}

    /**
     * Set the value of priceCurrent.
     *
     * @param priceCurrent new value of priceCurrent
     */
    public void setPriceCurrent(Double priceCurrent) {this.priceCurrent = priceCurrent;}

    /**
     * Get the value of priceClosing.
     *
     * @return the value of priceClosing
     */
    public Double getPriceClosing() {return priceClosing;}

    /**
     * Set the value of priceClosing.
     *
     * @param priceClosing new value of priceClosing
     */
    public void setPriceClosing(Double priceClosing) {this.priceClosing = priceClosing;}

    /**
     * Get the value of numberOfSharesAvailable.
     *
     * @return the value of numberOfSharesAvailable
     */
    public Long getNumberOfSharesAvailable() {return numberOfSharesAvailable;}

    /**
     * Set the value of numberOfSharesAvailable.
     *
     * @param numberOfSharesAvailable new value of numberOfSharesAvailable
     */
    public void setNumberOfSharesAvailable(Long numberOfSharesAvailable) {

        this.numberOfSharesAvailable = numberOfSharesAvailable;
    }

    /**
     * Get the value of numberOfSharesSold.
     *
     * @return the value of numberOfSharesSold
     */
    public Long getNumberOfSharesSold() {return numberOfSharesSold;}

    /**
     * Set the value of numberOfSharesSold.
     *
     * @param numberOfSharesSold new value of numberOfSharesSold
     */
    public void setNumberOfSharesSold(Long numberOfSharesSold) {this.numberOfSharesSold = numberOfSharesSold;}

    /**
     * Formats a double value as currency.
     *
     * @param currency double value
     * @return double value formatted as currency.
     */
    public String formatCurrency(Double currency) {

        if (currency != null) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            return currencyFormat.format(currency);
        }
        return "";
    }
}
