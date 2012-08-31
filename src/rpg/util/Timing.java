package rpg.util;

public final class Timing {
  private Timing() {}

  // The difference between unix time and what System.nanoTime() gives.
  private static final double offset;

  static {
    double arbTime = System.nanoTime() * 1e-9;
    double unixTime = System.currentTimeMillis() * 1e-3;
    offset = unixTime - arbTime;
  }

  public static double currentTime() {
    return System.currentTimeMillis() * 1e-3;
  }

  public static double currentTimePrecise() {
    return System.nanoTime() * 1e-9 + offset;
  }
}
