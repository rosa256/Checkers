package checkers;

public class TimeCounter {
    public long getStartingTime() {
        return this.startingTime;
    }

    public long getElapsedTime() {
        long elapsedTime = System.currentTimeMillis() - this.startingTime;
        return elapsedTime;
    }

    long startingTime = System.currentTimeMillis();
}
