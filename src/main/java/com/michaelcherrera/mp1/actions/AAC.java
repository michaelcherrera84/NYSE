package com.michaelcherrera.mp1.actions;

/**
 * AAC Holdings Inc stock singleton.
 */
public class AAC extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AAC";

    /**
     * The single AAC object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AAC object.
     */
    private AAC() {symbol = SYMBOL;}

    /**
     * Returns the single AAC object.
     *
     * @return the single AAC object
     */
    public static StockAction getStockAction() {

        // If the object is not yet created, create it.
        if (stockAction == null)
            stockAction = new AAC();

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
