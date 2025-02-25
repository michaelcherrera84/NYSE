package com.michaelcherrera.mp1.actions;

/**
 * Alibaba Group Holding Ltd stock singleton.
 */
public class BABA extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "BABA";

    /**
     * The single BABA object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the BABA object.
     */
    private BABA() {}

    /**
     * Returns the single BABA object.
     *
     * @return the single BABA object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new BABA();

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