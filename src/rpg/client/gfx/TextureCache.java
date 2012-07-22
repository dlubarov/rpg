package rpg.client.gfx;

import java.io.IOException;
import java.io.InputStream;
import rpg.core.Logger;
import rpg.util.ResourceCache;

public final class TextureCache extends ResourceCache<Texture> {
  public static final TextureCache singleton = new TextureCache();

  private TextureCache() {
    super(10000);
  }

  public void bind(String name) {
    get(name).bind();
  }

  @Override
  protected Texture loadResource(String name) {
    name = "/tex/" + name + ".png";
    try {
      InputStream stream = TextureCache.class.getResourceAsStream(name);
      if (stream == null)
        Logger.error("Could not find resource %s.", name);
      return TextureLoader.load(stream);
    } catch (IOException e) {
      Logger.error(e, "Failed to load texture %s.", name);
      return null;
    }
  }
}
