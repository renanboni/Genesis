public class GameLoop implements Runnable {

    private boolean isRunning;
    private final double updateRate = 1.0d / 60.d;

    private long nextStatTime;
    private int fps, ups;

    @Override
    public void run() {
        isRunning = true;

        double accumulator = 0.0;
        long currentTime, lastUpdate = System.currentTimeMillis();

        nextStatTime = System.currentTimeMillis() + 1000;

        while (isRunning) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;

            accumulator += lastRenderTimeInSeconds;

            lastUpdate = currentTime;

            while (accumulator > updateRate) {
                update();
                accumulator -= updateRate;
            }
            render();

            printStats();
        }
    }

    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime) {
            System.out.println("FPS: " + fps + " UPS: " + ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void render() {
        fps++;
    }

    private void update() {
        ups++;
    }
}
