package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;

public abstract class Label extends Widget {
  private static final FontRenderer fontRenderer = FontRendererCache.singleton.get("Arial-13");

  private final Alignment alignment;

  public Label(Alignment alignment) {
    this.alignment = alignment;
  }

  protected abstract String getContent();

  @Override
  public int getMinWidth() {
    return fontRenderer.getWidth(getContent());
  }

  @Override
  public int getMinHeight() {
    return fontRenderer.getHeight();
  }

  @Override
  public boolean stretchHorizontally() {
    return false;
  }

  @Override
  public boolean stretchVertically() {
    return false;
  }

  @Override
  public void render() {
    int extraH = bounds.h() - getMinHeight();
    int base = bounds.y2() - extraH / 2;
    fontRenderer.draw(getContent(), Color.BLACK,
        bounds.x1(), bounds.y2(), bounds.w(), alignment);
  }
}
