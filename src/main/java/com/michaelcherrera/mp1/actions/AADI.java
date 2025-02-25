package com.michaelcherrera.mp1.actions;

/**
 * Aadi Bioscience Inc stock singleton.
 */
public class AADI extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AADI";

    /**
     * The single AADI object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AADI object.
     */
    private AADI() {}

    /**
     * Returns the single AADI object.
     *
     * @return the single AADI object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AADI();

        symbol = SYMBOL;

        return stockAction;
    }

    /**
     * Returns the stock symbol.
     *
     * @return the stock symbol
     */
    @Override
    public String getName() {return SYMBOL;}
}