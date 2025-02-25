package com.michaelcherrera.mp1.actions;

/**
 * A O Smith Corp stock singleton.
 */
public class AOS extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AOS";

    /**
     * The single AOS object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AOS object.
     */
    private AOS() {}

    /**
     * Returns the single AOS object.
     *
     * @return the single AOS object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AOS();

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
