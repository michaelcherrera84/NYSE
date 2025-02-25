package com.michaelcherrera.mp1.actions;

/**
 * Bank of America Corp stock singleton.
 */
public class BAC extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "BAC";

    /**
     * The single BAC object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the BAC object.
     */
    private BAC() {}

    /**
     * Returns the single BAC object.
     *
     * @return the single BAC object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new BAC();

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
