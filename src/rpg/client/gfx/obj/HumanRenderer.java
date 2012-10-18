package rpg.client.gfx.obj;

import rpg.client.gfx.GLUtil;
import rpg.game.shape.HumanShape;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

public class HumanRenderer {
  private final HumanShape humanShape;

  public HumanRenderer(HumanShape humanShape) {
    this.humanShape = humanShape;
  }

  public void render() {
    glDisable(GL_TEXTURE_2D);
    glColor3f(0, 0, 0);
    glBegin(GL_TRIANGLES);
    GLUtil.glVertex(humanShape.getLeftFoot());
    GLUtil.glVertex(humanShape.getRightFoot());
    GLUtil.glVertex(humanShape.getHeadTop());
    glEnd();
    new SphereRenderer(humanShape.getHead()).render();
    glEnable(GL_TEXTURE_2D);
  }
}
