package rpg.client.gfx.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.util.HashMap;
import java.util.Map;
import rpg.client.gfx.ColorUtil;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class FontRenderer {
  private final Font font;
  private final Map<Character, Glyph> glyphs = new HashMap<Character, Glyph>();
  private GlyphPage currentPage;
  private final GlyphVectorCache glyphVectorCache;

  public FontRenderer(Font font) {
    currentPage = new GlyphPage(this.font = font);
    glyphVectorCache = new GlyphVectorCache(font, currentPage.renderContext);
  }

  public int getWidth(String s) {
    return (int) getGlyphVector(s).getVisualBounds().getWidth();
  }

  public int getHeight(String s) {
    return (int) getGlyphVector(s).getVisualBounds().getHeight();
  }

  public int getHeight() {
    // TODO: There are better ways...
    return getHeight("Hg");
  }

  public void draw(String s, Color color, int x, int y) {
    // TODO: Optimize with display list cache, or at least GlyphVector.
    glPushMatrix();
    glTranslatef(x, y, 0);
    drawNoMemo(s, color);
    glPopMatrix();
  }

  public void draw(String s, Color color, int x, int y, int width, Alignment alignment) {
    if (alignment == Alignment.RIGHT_ALIGNED) {
      x += width - getWidth(s);
    }
    if (alignment == Alignment.CENTER_ALIGNED) {
      x += (width - getWidth(s)) / 2;
    }
    draw(s, color, x, y);
  }

  private void drawNoMemo(String s, Color color) {
    char[] chars = s.toCharArray();
    GlyphVector vector = font.layoutGlyphVector(
        currentPage.renderContext, // TODO: weird to use currentPage
        chars, 0, chars.length, Font.LAYOUT_LEFT_TO_RIGHT);
    for (int glyphIndex = 0; glyphIndex < vector.getNumGlyphs(); ++glyphIndex) {
      int charIndex = vector.getGlyphCharIndex(glyphIndex);
      int codePoint = s.codePointAt(charIndex);
      // TODO: bounds of space character
      Rectangle bounds = vector.getGlyphPixelBounds(glyphIndex, currentPage.renderContext, 0, 0);
      // FIXME: the glyph generation overrides a previously set color?
      Glyph glyph = get((char) codePoint); // TODO: sacrifices proper unicode support
      glyph.texture.bind();
      ColorUtil.bindColor(color);
      glBegin(GL_QUADS);
      double x1 = glyph.x1 / (double) glyph.texture.width,
             y1 = glyph.y1 / (double) glyph.texture.height,
             dx = glyph.dx / (double) glyph.texture.width,
             dy = glyph.dy / (double) glyph.texture.height;
      glTexCoord2d(x1, y1);
      glVertex2d(bounds.getMinX(), bounds.getMinY());
      glTexCoord2d(x1, y1 + dy);
      glVertex2d(bounds.getMinX(), bounds.getMaxY());
      glTexCoord2d(x1 + dx, y1 + dy);
      glVertex2d(bounds.getMaxX(), bounds.getMaxY());
      glTexCoord2d(x1 + dx, y1);
      glVertex2d(bounds.getMaxX(), bounds.getMinY());
      glEnd();
    }
  }

  private GlyphVector getGlyphVector(String s) {
    return glyphVectorCache.get(s);
  }

  private synchronized Glyph get(char c) {
    Glyph glyph = glyphs.get(c);
    if (glyph == null) {
      glyphs.put(c, glyph = getNoMemo(c));
    }
    return glyph;
  }

  private Glyph getNoMemo(char c) {
    Glyph glyph = currentPage.add(c);
    if (glyph == null) {
      currentPage = new GlyphPage(font);
      glyph = currentPage.add(c);
      assert glyph != null;
    }
    return glyph;
  }
}
