package rpg.client.gfx;

import rpg.util.math.Vector2;
import rpg.util.math.Vector3;

import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex3d;

public final class GLUtil {
  private GLUtil() {}

  public static void glVertex(Vector2 v) {
    glVertex2d(v.x, v.y);
  }

  public static void glVertex(Vector3 v) {
    glVertex3d(v.x, v.y, v.z);
  }
}
