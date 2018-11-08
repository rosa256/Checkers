package checkers;

class TimeCounter {

    long getElapsedTime() {
        return System.currentTimeMillis() - this.startingTime;
    }

    private long startingTime = System.currentTimeMillis();
}
