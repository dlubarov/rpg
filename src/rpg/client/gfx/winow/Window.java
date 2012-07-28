package rpg.client.gfx.winow;

import rpg.client.gfx.GraphicsMode;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;

public abstract class Window {
  protected static final int BAR_HEIGHT = 20;

  public String caption;

  protected Window(String caption) {
    this.caption = caption;
  }

  protected abstract int x1();
  protected abstract int y1();
  protected abstract int contentW();
  protected abstract int contentH();

  protected final int x2() {
    return x1() + contentW();
  }

  protected final int y2() {
    return y1() + BAR_HEIGHT;
  }

  protected final int y3() {
    return y1() + BAR_HEIGHT + contentH();
  }

  protected abstract void renderBackground();
  protected abstract void renderContent();

  public void render() {
    renderBackground();
    renderContent();
    GraphicsMode.start2D();
    glDisable(GL_TEXTURE_2D);
    renderBar();
    renderOutline();
    GraphicsMode.end2D();
    glEnable(GL_TEXTURE_2D);
  }

  private void renderBar() {
    glColor3d(1, .7, 1);
    glBegin(GL_QUADS);
    glVertex2i(x1(), y1());
    glVertex2i(x1(), y2());
    glVertex2i(x2(), y2());
    glVertex2i(x2(), y1());
    glEnd();
  }

  private void renderOutline() {
    glColor3d(.7, .7, .7);
    glBegin(GL_LINES);

    // Horizontal lines.
    glVertex2d(x1(), y1() + .5);
    glVertex2d(x2(), y1() + .5);
    glVertex2d(x1(), y2() + .5);
    glVertex2d(x2(), y2() + .5);
    glVertex2d(x1(), y3() - .5);
    glVertex2d(x2(), y3() - .5);

    // Vertical lines.
    glVertex2d(x1() + .5, y1());
    glVertex2d(x1() + .5, y3());
    glVertex2d(x2() - .5, y1());
    glVertex2d(x2() - .5, y3());

    glEnd();
  }
}
