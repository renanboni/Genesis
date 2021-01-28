package game;

public class Time {

    private int updatesSinceStart;

    public Time() {
        this.updatesSinceStart = 0;
    }

    public int getUpdatesFromSecond(int seconds) {
        return seconds * GameLoop.UPDATES_PER_SECONDS;
    }
}
