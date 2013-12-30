package rpg.client.gfx.widget.winow;

import java.awt.Color;
import rpg.client.gfx.ColorUtil;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;
import rpg.client.gfx.widget.Bounds;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;

public abstract class Window {
  protected static final int BAR_HEIGHT = 20;

  private final WindowButton[] buttons;

  protected Window(WindowButton... buttons) {
    this.buttons = buttons;
  }

  private static FontRenderer getFontRenderer() {
    return FontRendererCache.singleton.get("Arial-BOLD-14");
  }

  public abstract String getCaption();

  public abstract boolean isFocused();

  public abstract int x1();
  public abstract int y1();
  public abstract int contentW();
  public abstract int contentH();

  public final int x2() {
    return x1() + contentW();
  }

  public final int y2() {
    return y1() + BAR_HEIGHT;
  }

  public final int y3() {
    return y1() + BAR_HEIGHT + contentH();
  }

  public final int totalH() {
    return BAR_HEIGHT + contentH();
  }

  public Bounds bounds() {
    return new Bounds(x1(), y2(), x2(), y3());
  }

  public boolean inWindow(int x, int y) {
    return x1() <= x && x <= x2()
        && y1() <= y && y <= y3();
  }

  public boolean inTitleBar(int x, int y) {
    return x1() <= x && x <= x2()
        && y1() <= y && y <= y2();
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
    double sat = .05, val = 1;
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
    glColor3d(.7, .7, .7);
    glBegin(GL_QUADS);
    if (isFocused()) {
      ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.00));
    }
    glVertex2i(x1(), y1());
    if (isFocused()) {
      ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.25));
    }
    glVertex2i(x1(), y2());
    if (isFocused()) {
      ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.50));
    }
    glVertex2i(x2(), y2());
    if (isFocused()) {
      ColorUtil.bindColor(ColorUtil.phaseColor(sat, val, 0.75));
    }
    glVertex2i(x2(), y1());
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  protected void renderCaption() {
    glColor3f(0, 0, 0);
    getFontRenderer().draw(getCaption(), Color.WHITE,
        x1(), y1() + 2,
        contentW(), Alignment.CENTER_ALIGNED);
  }

  private void renderOutline() {
    glDisable(GL_TEXTURE_2D);
    glColor3d(.5, .5, .5);
    glBegin(GL_LINES);

    // Horizontal lines.
    glVertex2d(x1(), y1() + .5);
    glVertex2d(x2(), y1() + .5);
    glVertex2d(x1(), y2() - .5);
    glVertex2d(x2(), y2() - .5);
    glVertex2d(x1(), y3() - .5);
    glVertex2d(x2(), y3() - .5);

    // Vertical lines.
    glVertex2d(x1() + .5, y1() + .5);
    glVertex2d(x1() + .5, y3() - .5);
    glVertex2d(x2() - .5, y1() + .5);
    glVertex2d(x2() - .5, y3() - .5);

    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  private void renderButtons() {
    for (int i = 0; i < buttons.length; ++i)
      buttons[i].render(getButtonBounds(i));
  }

  private Bounds getButtonBounds(int index) {
    int pad = 3;
    int size = BAR_HEIGHT - 2 * pad;
    int x  = x2() - pad - index * size;
    return new Bounds(x - size, y1() + pad, x, y2() - pad);
  }

  public void onClick(int x, int y) {
    for (int i = 0; i < buttons.length; ++i)
      if (getButtonBounds(i).contains(x, y))
        buttons[i].onClick(this);
  }

  public abstract void close();
}
