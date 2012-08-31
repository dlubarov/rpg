package rpg.client.gfx;

import java.awt.Color;
import rpg.util.Timing;

import static org.lwjgl.opengl.GL11.glColor3f;

public final class ColorUtil {
  private ColorUtil() {}

  public static void bindColor(Color color) {
    float[] rgb = color.getRGBComponents(null);
    glColor3f(rgb[0], rgb[1], rgb[2]);
  }

  public static double phase() {
    return Timing.currentTime() * .05 % 1;
  }

  public static double phase(double offset) {
    return (phase() + offset) % 1;
  }

  public static Color phaseColor(double sat, double val, double offset) {
    return Color.getHSBColor(
        (float) phase(offset),
        (float) sat,
        (float) val);
  }
}
