package rpg.client.gfx.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import rpg.util.ResourceCache;

public class GlyphVectorCache extends ResourceCache<GlyphVector> {
  private final Font font;
  private final FontRenderContext context;

  public GlyphVectorCache(Font font, FontRenderContext context) {
    super(300);
    this.font = font;
    this.context = context;
  }

  @Override
  protected GlyphVector loadResource(String s) {
    return font.createGlyphVector(context, s);
  }
}
