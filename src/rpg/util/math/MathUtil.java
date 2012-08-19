package rpg.util.math;

public final class MathUtil {
  private MathUtil() {}

  public static double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
  }
}
