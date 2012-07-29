package rpg.client.gfx.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import rpg.client.gfx.Texture;
import rpg.client.gfx.TextureLoader;
import rpg.util.Logger;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_FRACTIONALMETRICS;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_ON;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
import static org.lwjgl.opengl.GL11.GL_MAX_TEXTURE_SIZE;
import static org.lwjgl.opengl.GL11.glGetInteger;

// TODO: Clean up.
final class GlyphPage {
  private static final int MAX_TEXTURE_SIZE = 256; // TODO: increase
  private static final int PADDING = 8;
  private static final int SIZE;

  static {
    int maxTextureSize = glGetInteger(GL_MAX_TEXTURE_SIZE);
    SIZE = Math.min(maxTextureSize, MAX_TEXTURE_SIZE);
    Logger.debug("Glyph page size is %d (GL_MAX_TEXTURE_SIZE=%d)",
        SIZE, maxTextureSize);
  }

  private final Font font;
  private int x = PADDING, y = PADDING, rowHeight = 0;
  private boolean full = false;

  // TODO: the scratch buffers are static in slick; can they be here?
  // TODO: BufferedImage could be smaller (MAX_CHAR_SIZE)
  private final BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
  private final Graphics2D graphics = (Graphics2D) image.getGraphics();
  private Texture texture = Texture.createBlank(SIZE, SIZE);
  private final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(SIZE * SIZE * 4)
      .order(ByteOrder.LITTLE_ENDIAN);
  private final IntBuffer intBuffer = byteBuffer.asIntBuffer();
  public final FontRenderContext renderContext = graphics.getFontRenderContext();

  GlyphPage(Font font) {
    Logger.debug("Creating new glyph page for %s.", font);
    this.font = font;
    graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
    graphics.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
  }

  public Glyph add(char c) {
    assert !full;
    GlyphVector vector = font.createGlyphVector(renderContext, new char[] {c});
    GlyphMetrics metrics = vector.getGlyphMetrics(0);
    Rectangle bounds = vector.getGlyphPixelBounds(0, renderContext, 0, 0);
    int w = (int) (bounds.getWidth()
          - Math.min(metrics.getLSB(), 0)
          - Math.min(metrics.getRSB(), 0)),
        h = (int) bounds.getHeight();
    if (w > SIZE || h > SIZE)
      Logger.error("Character %c of font %s is too large.", c, font);
    boolean newRow = x + w >= SIZE;
    if (newRow) {
      x = PADDING;
      y += rowHeight + PADDING;
      if (y + h > SIZE) {
        markAsFull();
        return null;
      }
      rowHeight = h;
    }
    else
      rowHeight = Math.max(rowHeight, h);
    // FIXME do this properly
    //graphics.setComposite(AlphaComposite.Clear);
    //graphics.fillRect(0, 0, w, h);
    //graphics.setComposite(AlphaComposite.SrcOver);
    graphics.setColor(Color.WHITE);
    graphics.setFont(font);
    graphics.drawString(Character.toString(c), x, y + h);

    /*WritableRaster raster = image.getRaster();
    int[] row = new int[w];
    for (int yy = 0; yy < h; ++yy) {
      raster.getDataElements(x, y + yy, w, 1, row);
      intBuffer.put(row);
    }
    texture.bind();*/
    //glTexSubImage2D(GL_TEXTURE_2D, 0, x, y, w, h, GL12.GL_BGRA, GL_UNSIGNED_BYTE, byteBuffer);
    texture = TextureLoader.load(image);
    //intBuffer.clear();
    Glyph glyph = new Glyph(texture, x, y, w, h);
    graphics.setColor(Color.GREEN);
    graphics.drawRect(x, y, w, h);
    try {
      // FIXME: remove!!!! temporary!
      ImageIO.write(image, "png", new File(font.getFontName() + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    x += w + PADDING;
    return glyph;
  }

  private void markAsFull() {
    full = true;
    // TODO: log info properly
    Logger.info("%s became full after adding %d characters; utilization=%.0f%%.",
        -1, -1);
  }
}
