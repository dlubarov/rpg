package rpg.client;

public final class FPSManager {
  private FPSManager() {}

  public static final int targetFPS = 80;
  private static final double intervalSize = 30;

  private static double averageFPS = targetFPS;
  private static long lastTime = System.nanoTime() - (int) 1e9 / targetFPS;

  public static int getFps() {
    return (int) Math.round(averageFPS);
  }

  public static void newFrame() {
    long time = System.nanoTime();
    double currentFPS = 1e9 / (time - lastTime);
    averageFPS = averageFPS * (intervalSize - 1) / intervalSize
               + currentFPS / intervalSize;
    lastTime = time;
  }
}
