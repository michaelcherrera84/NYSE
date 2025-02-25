package com.michaelcherrera.mp1.actions;

/**
 * AMC Entertainment Holdings Inc stock singleton.
 */
public class AMC extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AMC";

    /**
     * The single AMC object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AMC object.
     */
    private AMC() {}

    /**
     * Returns the single AMC object.
     *
     * @return the single AMC object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AMC();

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
