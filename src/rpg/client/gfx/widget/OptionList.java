package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
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

public class OptionList<T> extends Widget {
  private final T[] options;
  private Integer selectedOpt = null;

  public OptionList(T... options) {
    this.options = options;
  }

  private static FontRenderer getFontRenderer() {
    return FontRendererCache.singleton.get("Arial-13");
  }

  @Override public int getMinWidth() {
    int maxWidth = 0;
    for (T opt : options)
      maxWidth = Math.max(maxWidth, getFontRenderer().getWidth(opt.toString()));
    return maxWidth;
  }

  @Override public int getMinHeight() {
    return getFontRenderer().getHeight() * options.length;
  }

  @Override public boolean stretchHorizontally() {
    return false;
  }

  @Override public boolean stretchVertically() {
    return false;
  }

  @Override public void render() {
    glDisable(GL_TEXTURE_2D);
    if (isFrozen())
      glColor3f(.8f, .8f, .8f);
    else
      glColor3f(1, 1, 1);
    glBegin(GL_QUADS);
    glVertex2i(bounds.x1(), bounds.y1());
    glVertex2i(bounds.x1(), bounds.y2());
    glVertex2i(bounds.x2(), bounds.y2());
    glVertex2i(bounds.x2(), bounds.y1());
    glEnd();

    glColor3d(.5, .5, .5);
    glBegin(GL_LINE_LOOP);
    glVertex2d(bounds.x1() + .5, bounds.y1() + .5);
    glVertex2d(bounds.x1() + .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y1() + .5);
    glEnd();
    glEnable(GL_TEXTURE_2D);

    for (int i = 0; i < options.length; ++i) {
      Color c = Color.BLACK;
      if (selectedOpt != null && selectedOpt == i)
        c = Color.GREEN;

      getFontRenderer().draw(options[i].toString(), c,
          bounds.x1(), getOptionBounds(i).y1());
    }
  }

  public void onClick(int x, int y) {
    for (int i = 0; i < options.length; ++i)
      if (getOptionBounds(i).contains(x, y))
        selectedOpt = i;
  }

  private Bounds getOptionBounds(int i) {
    return new Bounds(bounds.x1(), bounds.y1() + i * getFontRenderer().getHeight(),
                      bounds.x2(), bounds.y1() + (i + 1) * getFontRenderer().getHeight());
  }

  public T getSelected() {
    if (selectedOpt == null)
      return null;
    return options[selectedOpt];
  }
}
