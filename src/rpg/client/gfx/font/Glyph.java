package rpg.client.gfx.font;

import rpg.client.gfx.Texture;

public class Glyph {
  public final Texture texture;
  public final int x1, y1, dx, dy;

  public Glyph(Texture texture, int x1, int y1, int dx, int dy) {
    this.texture = texture;
    this.x1 = x1;
    this.y1 = y1;
    this.dx = dx;
    this.dy = dy;
  }
}
