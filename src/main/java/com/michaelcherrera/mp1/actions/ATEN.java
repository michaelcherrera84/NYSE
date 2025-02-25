package com.michaelcherrera.mp1.actions;

/**
 * A10 Networks Inc stock singleton.
 */
public class ATEN extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "ATEN";

    /**
     * The single ATEN object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the ATEN object.
     */
    private ATEN() {}

    /**
     * Returns the single ATEN object.
     *
     * @return the single ATEN object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new ATEN();

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
