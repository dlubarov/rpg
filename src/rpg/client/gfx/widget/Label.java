package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.font.FontRenderer;
import rpg.client.gfx.font.FontRendererCache;

public abstract class Label extends Widget {
  private static final FontRenderer fontRenderer = FontRendererCache.singleton.get("Arial-13");

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
    fontRenderer.draw(getContent(), color,
        bounds.x1(), bounds.y1(),
        bounds.w(), alignment);
  }
}
