package me.InfoPaste.MobLoot.objects;

public class Timer {

    private long start;
    private long stop;
    private long time;

    public Timer() {
        start = -1;
        stop = -1;
        time = -1;
    }

    public Timer(boolean start) {
        if (start) {
            this.start = System.currentTimeMillis();
        } else {
            this.start = -1;
        }

        stop = -1;
        time = -1;
    }

    /*
     * Starts timer, even if timer has already been started
     */
    public void start() {
        start = System.currentTimeMillis();
        return;
    }

    /*
     * Returns start time
     */
    public long getStart() {
        return start;
    }

    /*
     * Ends timer
     */
    public void stop() {
        stop = System.currentTimeMillis();
        return;
    }

    /*
     * Returns stop time
     */
    public long getStop() {
        return stop;
    }

    /*
     * Calculates time between start and finish
     */
    public long time() {

        if (stop == -1 || start == -1) {
            return -1;
        }

        time = stop - start;
        return time;
    }
}
