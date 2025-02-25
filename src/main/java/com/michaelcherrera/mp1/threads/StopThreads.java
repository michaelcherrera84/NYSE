package com.michaelcherrera.mp1.threads;

/**
 * This interfaced provides the ability to stop the buying or selling of shares.
 *
 * @author Michael C. Herrera
 */
public interface StopThreads extends aTransaction {

    /**
     * Stops the continuous buying or selling of shares when true.
     *
     * @param stop true to stop the buying or selling of shares
     */
    void stopThreads(boolean stop);
}
