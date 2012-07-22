package rpg.client.gfx;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

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
    TextureReleaser.singleton.add(this);
  }

  public void bind() {
    if (last != this) {
      glBindTexture(GL_TEXTURE_2D, id);
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

  private double endU() {
    return sourceImageWidth / (double) width;
  }

  private double endV() {
    return sourceImageHeight / (double) height;
  }

  public static void release(int id) {
    IntBuffer buf = IntBuffer.allocate(1);
    buf.put(id);
    buf.flip();
    glDeleteTextures(buf);
  }
}
