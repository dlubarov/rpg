package rpg.client.gfx.font;

import java.awt.Font;
import rpg.util.ResourceCache;

public class FontRendererCache extends ResourceCache<FontRenderer> {
  public static final FontRendererCache singleton = new FontRendererCache();

  private FontRendererCache() {
    super(100);
  }

  @Override
  protected FontRenderer loadResource(String fontDescriptor) {
    return new FontRenderer(Font.decode(fontDescriptor));
  }
}
