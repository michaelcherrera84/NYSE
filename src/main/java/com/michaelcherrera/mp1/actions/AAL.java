package com.michaelcherrera.mp1.actions;

/**
 * American Airlines Group Inc stock singleton.
 */
public class AAL extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AAL";

    /**
     * The single AAL object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AAL object.
     */
    private AAL() {}

    /**
     * Returns the single AAL object.
     *
     * @return the single AAL object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AAL();

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
