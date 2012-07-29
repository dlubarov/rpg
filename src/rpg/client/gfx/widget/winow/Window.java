package rpg.client.gfx.widget.winow;

import rpg.client.gfx.ColorUtil;
import rpg.client.gfx.font.FontRendererCache;
import rpg.client.gfx.widget.Bounds;
import rpg.util.Logger;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;

public abstract class Window {
  protected static final int BAR_HEIGHT = 20;

  public String caption;
  private Button[] buttons;

  protected Window(String caption, Button... buttons) {
    this.caption = caption;
    this.buttons = buttons;
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

  protected abstract void renderContent();

  public void render() {
    renderBackground();
    renderContent();
    renderBar();
    renderCaption();
    renderOutline();
    renderButtons();
  }

  protected void renderBackground() {
    double sat = .1, val = 1;
    glDisable(GL_TEXTURE_2D);
    glBegin(GL_QUADS);
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.00));
    glVertex2i(x1(), y2());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.25));
    glVertex2i(x1(), y3());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.50));
    glVertex2i(x2(), y3());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.75));
    glVertex2i(x2(), y2());
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  private void renderBar() {
    double sat = .4, val = 1;
    glDisable(GL_TEXTURE_2D);
    glColor3d(1, .7, 1);
    glBegin(GL_QUADS);
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.00));
    glVertex2i(x1(), y1());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.25));
    glVertex2i(x1(), y2());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.50));
    glVertex2i(x2(), y2());
    ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.75));
    glVertex2i(x2(), y1());
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  protected void renderCaption() {
    glColor3f(0, 0, 0);
    glPushMatrix();
    glTranslatef(x1(), y2() - 4, 0);
    FontRendererCache.singleton.get("Arial-BOLD-18").draw(caption);
    glPopMatrix();
  }

  private void renderOutline() {
    glDisable(GL_TEXTURE_2D);
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
    glEnable(GL_TEXTURE_2D);
  }

  private void renderButtons() {
    int pad = 2, size = BAR_HEIGHT - 2 * pad;
    int x = x2() - pad;
    for (Button b : buttons) {
      Bounds bounds = new Bounds(x - size, y1() + pad, x, y2() - pad);
      b.render(bounds);
      x -= size + pad;
    }
  }
}
