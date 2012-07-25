package rpg.client.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;
import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public final class TextureLoader {
  private TextureLoader() {}

  private static final ColorModel glColorModel = new ComponentColorModel(
      ColorSpace.getInstance(ColorSpace.CS_sRGB),
      new int[] {8, 8, 8, 0}, false, false,
      ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);

  private static final ColorModel glAlphaColorModel = new ComponentColorModel(
      ColorSpace.getInstance(ColorSpace.CS_sRGB),
      new int[] {8, 8, 8, 8}, true, false,
      ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

  public static Texture load(BufferedImage image) {
    IntBuffer buf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
    glGenTextures(buf);
    int textureId = buf.get(0);
    Texture texture = new Texture(textureId,
        getPowerOf2(image.getWidth()), getPowerOf2(image.getHeight()),
        image.getWidth(), image.getHeight());
    glBindTexture(GL_TEXTURE_2D, textureId);
    int sourcePixelFormat = image.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;
    ByteBuffer textureBuffer = convertImageData(image, texture); // FIXME
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texture.width, texture.height,
        0, sourcePixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
    return texture;
  }

  public static Texture load(InputStream data) throws IOException {
    return load(ImageIO.read(data));
  }

  private static ByteBuffer convertImageData(BufferedImage image, Texture texture) {
    boolean alpha = image.getColorModel().hasAlpha();
    WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
        texture.width, texture.height, alpha ? 4 : 3, null);
    BufferedImage texImage = new BufferedImage(
        alpha ? glAlphaColorModel : glColorModel,
        raster, false, new Hashtable());
    Graphics g = texImage.getGraphics();

    // TODO: Remove green background, it's for debugging only.
    g.setColor(Color.GREEN);
    //g.fillRect(0, 0, texImage.getWidth(), texImage.getHeight());
    g.drawImage(image, 0, 0, null);

    byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();
    ByteBuffer result = ByteBuffer.allocateDirect(data.length);
    result.order(ByteOrder.nativeOrder());
    result.put(data, 0, data.length);
    result.flip();
    return result;
  }

  // TODO: optimize
  private static int getPowerOf2(int n) {
    int result = 2;
    while (result < n) {
      result <<= 1;
    }
    return result;
  }
}
