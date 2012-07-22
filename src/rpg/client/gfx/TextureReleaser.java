package rpg.client.gfx;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public final class TextureReleaser extends Thread {
  public static TextureReleaser singleton = new TextureReleaser();

  private final Map<PhantomReference<Texture>, Integer> textureIds =
      new HashMap<PhantomReference<Texture>, Integer>();

  private final ReferenceQueue<Texture> queue = new ReferenceQueue<Texture>();

  private TextureReleaser() {
    super("Texture Releaser");
    setDaemon(true);
  }

  public void add(Texture texture) {
    PhantomReference<Texture> reference = new PhantomReference<Texture>(texture, queue);
    textureIds.put(reference, texture.id);
  }

  @Override
  public void run() {
    for (;;)
      try {
        Reference<? extends Texture> reference = queue.remove();
        int textureId = textureIds.get(reference);
        Texture.release(textureId);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }
}
