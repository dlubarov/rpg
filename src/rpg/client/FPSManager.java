package rpg.client;

import rpg.util.Timing;

public final class FPSManager {
  private FPSManager() {}

  public static final int targetFPS = 80;
  private static final double intervalSize = 30;

  private static double averageFPS = targetFPS;
  private static double lastTime = Timing.currentTimePrecise() - 1.0 / targetFPS;

  public static int getFps() {
    return (int) Math.round(averageFPS);
  }

  // Returns the time elapsed since the last frame, in seconds.
  public static double newFrame() {
    double time = Timing.currentTimePrecise();
    double dt = time - lastTime;
    double currentFPS = 1 / dt;
    averageFPS = averageFPS * (intervalSize - 1) / intervalSize
               + currentFPS / intervalSize;
    lastTime = time;
    return dt;
  }
}
