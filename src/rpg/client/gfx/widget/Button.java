package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.Alignment;
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

public abstract class Button extends Widget {
  private static final int PAD_TOP = 2, PAD_BOTTOM = 5, PAD_SIDES = 6;

  private final String content;

  private static FontRenderer getFontRenderer() {
    return FontRendererCache.singleton.get("Arial-BOLD-12");
  }

  public Button(String content) {
    this.content = content;
  }

  protected abstract void onClick();

  @Override public int getMinWidth() {
    return getFontRenderer().getWidth(content) + 2 * PAD_SIDES;
  }

  @Override public int getMinHeight() {
    return PAD_TOP + getFontRenderer().getHeight() + PAD_BOTTOM;
  }

  @Override public boolean stretchHorizontally() {
    return false;
  }

  @Override public boolean stretchVertically() {
    return false;
  }

  @Override public void onClick(int x, int y) {
    if (!isFrozen())
      onClick();
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

    getFontRenderer().draw(content, Color.BLACK,
        bounds.x1(), bounds.y1() + PAD_TOP, bounds.w(), Alignment.CENTER_ALIGNED);
  }
}
