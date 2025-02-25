let timer;
let stockHolders = [0, 0, 0, 0, 0, 0];

/**
 * Starts or stops the timer depending on the parameter
 *
 * @param {int} start when true the method will start the timer, when false it will stop the timer
 * @param {int} stockHolder the stockHolder making the call.
 */
function startTimer(start, stockHolder) {
    clearTimeout(timer);
    stockHolders[stockHolder] = start;

    // Stop the timer. If any stockholder is still buying/selling, restart the timer.
    if (start === 0) {
        clearTimeout(timer);
        for (let i = 0; i < stockHolders.length; i++) {
            if (stockHolders[i] === 1) {
                timer = setTimeout(() => startTimer(1, i), 500);
                break;
            }
        }
        return;
    }

    // Start the timer.
    if (start === 1) {
        // Call the bean via p:remoteCommand.
        timer = setTimeout(() => startTimer(1, stockHolder), 500);
        callRemoteCommand();
    } else
        clearTimeout(timer);
}