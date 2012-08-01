package rpg.client.gfx;

import java.awt.image.BufferedImage;
import rpg.client.Client;
import rpg.util.Logger;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2d;

public final class Texture {
  private static Texture last = null;

  public final int id;
  public final int width, height;
  public final int sourceImageWidth, sourceImageHeight;

  public Texture(int id, int width, int height, int sourceImageWidth, int sourceImageHeight) {
    this.id = id;
    this.width = width;
    this.height = height;
    this.sourceImageWidth = sourceImageWidth;
    this.sourceImageHeight = sourceImageHeight;
    TextureReleaser.add(this);
    Logger.debug("New texture of ID %d.", id);
  }

  public static Texture createBlank(int width, int height) {
    try {
      return TextureLoader.load(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void bind() {
    if (last != this) {
      glBindTexture(GL_TEXTURE_2D, id);
      last = this;
    }
  }

  public void bind00() {
    glTexCoord2d(0, 0);
  }

  public void bind01() {
    glTexCoord2d(0, endV());
  }

  public void bind10() {
    glTexCoord2d(endU(), 0);
  }

  public void bind11() {
    glTexCoord2d(endU(), endV());
  }

  // FIXME: make private
  public double endU() {
    return sourceImageWidth / (double) width;
  }

  // FIXME: make private
  public double endV() {
    return sourceImageHeight / (double) height;
  }

  static void release(int id) {
    Logger.debug("Releasing texture %d.", id);
    synchronized (Client.class) {
      glDeleteTextures(id);
    }
  }
}
