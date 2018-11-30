package data;

public class TimeCounter {

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.startingTime;
    }

    private long startingTime = System.currentTimeMillis();
}
