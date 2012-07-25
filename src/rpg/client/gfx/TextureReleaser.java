package rpg.client.gfx;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GLContext;
import rpg.util.Logger;

public final class TextureReleaser {
  private TextureReleaser() {}

  private static final Map<PhantomReference<Texture>, Integer> textureIds =
      new HashMap<PhantomReference<Texture>, Integer>();

  private static final ReferenceQueue<Texture> queue = new ReferenceQueue<Texture>();

  public static void add(Texture texture) {
    PhantomReference<Texture> reference = new PhantomReference<Texture>(texture, queue);
    textureIds.put(reference, texture.id);
  }

  public static void releaseDeadTextures() {
    Reference<? extends Texture> refTexture;
    while ((refTexture = queue.poll()) != null) {
      Integer textureID = textureIds.get(refTexture);
      assert textureID != null;
      Texture.release(textureID);
    }
  }
}
