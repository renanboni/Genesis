package game;

public class Time {

    private int updatesSinceStart;

    public Time() {
        this.updatesSinceStart = 0;
    }

    public int getUpdatesFromSecond(double seconds) {
        return (int) Math.round(seconds * GameLoop.UPDATES_PER_SECONDS);
    }

    public void update() {
        updatesSinceStart++;
    }

    public String getFormattedTime() {
        StringBuilder builder = new StringBuilder();

        int minutes = updatesSinceStart / GameLoop.UPDATES_PER_SECONDS / 60;
        int seconds = updatesSinceStart / GameLoop.UPDATES_PER_SECONDS % 60;

        if (minutes < 10) {
            builder.append(0);
        }

        builder.append(minutes);
        builder.append(":");

        if (seconds < 10) {
            builder.append(0);
        }

        builder.append(seconds);
        return builder.toString();
    }

    public boolean secondsDividableBy(double seconds) {
        return updatesSinceStart % getUpdatesFromSecond(seconds) == 0;
    }
}
