package checkers;

import java.util.Date;

public class timeCounter
{
    public static long getStartingTime() {
        return startingTime;
    }

    public static long getElapsedTime() {
        long elapsedTime = System.currentTimeMillis() - startingTime;
        return elapsedTime;
    }

    static long startingTime = System.currentTimeMillis();
}
