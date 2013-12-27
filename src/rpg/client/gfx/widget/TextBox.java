package rpg.client.gfx.widget;

import java.awt.Color;
import org.lwjgl.input.Keyboard;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;
import rpg.util.Timing;

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
  private static double BLINK_PHASE = 0.8, VISIBLE_FRACTION = 0.6;
  private static final int PAD_TOP = 2, PAD_BOTTOM = 5, PAD_SIDE = 3;

  private static final FontRenderer fontRenderer = FontRendererCache.singleton.get("Arial-13");

  protected String content;

  public TextBox(String initialContent) {
    content = initialContent;
  }

  public TextBox() {
    this("");
  }

  public String getContent() {
    return content;
  }

  protected String getContentToDraw() {
    return content;
  }

  @Override public int getMinWidth() {
    return fontRenderer.getWidth(content);
  }

  @Override public int getMinHeight() {
    return PAD_TOP + fontRenderer.getHeight() + PAD_BOTTOM;
  }

  @Override public boolean stretchHorizontally() {
    return true;
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

    if (isTotallyFocused())
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

    String text = getContentToDraw();
    if (isTotallyFocused() && cursorTime())
      text += '|';
    fontRenderer.draw(text, Color.BLACK,
        bounds.x1() + PAD_SIDE,
        bounds.y1() + PAD_TOP);
  }

  private static boolean cursorTime() {
    return Timing.currentTime() % BLINK_PHASE < BLINK_PHASE * VISIBLE_FRACTION;
  }

  @Override public void onKeyDown(int key) {
    if (isFrozen())
      return;

    switch (key) {
      case Keyboard.KEY_BACK:
        if (content.length() > 0)
          content = content.substring(0, content.length() - 1);
        break;
      default:
        char c = Keyboard.getEventCharacter();
        if (isPrintableChar(c) && c != '\t' && c != '\r' && c != '\n') {
          System.out.printf("---- typed %d (%c)\n", (int) c, c);
          content += c;
        }
    }
  }

  private static boolean isPrintableChar(char c) {
    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
    return !Character.isISOControl(c) &&
        c != Keyboard.CHAR_NONE &&
        block != null &&
        block != Character.UnicodeBlock.SPECIALS;
  }
}
