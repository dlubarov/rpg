package rpg.client.gfx.winow;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

public final class ChildWindow extends Window {
  private final int contentW, contentH;
  private int x1, y1;

  public ChildWindow(String caption, int contentW, int contentH, int x1, int y1) {
    super(caption);
    this.contentW = contentW;
    this.contentH = contentH;
    this.x1 = x1;
    this.y1 = y1;
  }

  @Override
  protected int x1() {
    return x1;
  }

  @Override
  protected int y1() {
    return y1;
  }

  @Override
  protected int contentW() {
    return contentW;
  }

  @Override
  protected int contentH() {
    return contentH;
  }

  @Override
  protected void renderBackground() {
    glDisable(GL_TEXTURE_2D);
    glColor3f(.8f, 1, .8f);
    glBegin(GL_QUADS);
    glVertex2i(x1(), y2());
    glVertex2i(x1(), y3());
    glVertex2i(x2(), y3());
    glVertex2i(x2(), y2());
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  @Override
  protected void renderContent() {
    // FIXME: render content
  }
}
