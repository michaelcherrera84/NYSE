package com.michaelcherrera.mp1.actions;

/**
 * AAR Corp stock singleton.
 */
public class AIR extends StockAction {

    /**
     * Stock symbol
     */
    public static final String SYMBOL = "AIR";

    /**
     * The single AIR object.
     */
    private static StockAction stockAction;

    /**
     * Constructs the AIR object.
     */
    private AIR() {}

    /**
     * Returns the single AIR object.
     *
     * @return the single AIR object
     */
    public static StockAction getStockAction() {

        if (stockAction == null)
            stockAction = new AIR();

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
