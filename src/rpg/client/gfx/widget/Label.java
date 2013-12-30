package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;

public abstract class Label extends Widget {
  private final Alignment alignment;
  private final Color color;

  public Label(Alignment alignment, Color color) {
    this.alignment = alignment;
    this.color = color;
  }

  public Label(Alignment alignment) {
    this(alignment, Color.BLACK);
  }

  public Label() {
    this(Alignment.LEFT_ALIGNED);
  }

  private static FontRenderer getFontRenderer() {
    return FontRendererCache.singleton.get("Arial-13");
  }

  protected abstract String getContent();

  @Override public int getMinWidth() {
    return getFontRenderer().getWidth(getContent());
  }

  @Override public int getMinHeight() {
    return getFontRenderer().getHeight();
  }

  @Override public boolean stretchHorizontally() {
    return false;
  }

  @Override public boolean stretchVertically() {
    return false;
  }

  @Override public void render() {
    getFontRenderer().draw(getContent(), color,
        bounds.x1(), bounds.y1(),
        bounds.w(), alignment);
  }
}
