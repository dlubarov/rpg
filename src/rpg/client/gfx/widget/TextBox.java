package rpg.client.gfx.widget;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
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

public class TextBox extends FocusableWidget {
  private static final int PAD_TOP = 2, PAD_BOTTOM = 5, PAD_SIDE = 3;

  private static final FontRenderer fontRenderer = FontRendererCache.singleton.get("Arial-13");

  private final String name;
  protected String content;

  public TextBox(String name, String initialContent) {
    this.name = name;
    content = initialContent;
  }

  public TextBox(String name) {
    this(name, "");
  }

  protected String getContentToDraw() {
    return content;
  }

  public Widget getWidget(String name) {
    return this.name.equals(name) ? this : null;
  }

  public String getValue(String name) {
    return this.name.equals(name) ? content : null;
  }

  @Override
  public int getMinWidth() {
    return fontRenderer.getWidth(content);
  }

  @Override
  public int getMinHeight() {
    return PAD_TOP + fontRenderer.getHeight() + PAD_BOTTOM;
  }

  @Override
  public boolean stretchHorizontally() {
    return true;
  }

  @Override
  public boolean stretchVertically() {
    return false;
  }

  @Override
  public void render() {
    glDisable(GL_TEXTURE_2D);
    glColor3f(1, 1, 1);
    glBegin(GL_QUADS);
    glVertex2i(bounds.x1(), bounds.y1());
    glVertex2i(bounds.x1(), bounds.y2());
    glVertex2i(bounds.x2(), bounds.y2());
    glVertex2i(bounds.x2(), bounds.y1());
    glEnd();

    if (isFocused())
      glColor3d(0, 1, 0);
    else
      glColor3d(.5, .5, .5);
    glBegin(GL_LINE_LOOP);
    glVertex2d(bounds.x1() + .5, bounds.y1() + .5);
    glVertex2d(bounds.x1() + .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y1() + .5);
    glEnd();
    glEnable(GL_TEXTURE_2D);

    fontRenderer.draw(getContentToDraw(), Color.BLACK,
        bounds.x1() + PAD_SIDE,
        bounds.y2() - PAD_BOTTOM);
  }

  @Override
  public void onKeyDown(int key) {
    switch (key) {
      case Keyboard.KEY_BACK:
        if (content.length() > 0)
          content = content.substring(0, content.length() - 1);
        break;
      default:
        char c = Keyboard.getEventCharacter();
        if (c != Keyboard.CHAR_NONE && c != '\t' && c != '\n')
          content += c;
    }
  }
}
