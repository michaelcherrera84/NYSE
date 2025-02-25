package com.michaelcherrera.mp1.actions;

/**
 * Amcor PLC stock singleton.
 */
public class AMCR extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AMCR";

    /**
     * The single AMCR object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AMCR object.
     */
    private AMCR() {}

    /**
     * Returns the single AMCR object.
     *
     * @return the single AMCR object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AMCR();

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
