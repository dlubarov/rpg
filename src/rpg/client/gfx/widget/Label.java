package rpg.client.gfx.widget;

import rpg.client.gfx.GraphicsMode;
import rpg.client.gfx.font.FontRendererCache;

public abstract class Label extends Widget {
  protected abstract String getContent();

  @Override
  public int getMinWidth() {
    // FIXME: hardcoded width
    return getContent().length() * 40;
  }

  @Override
  public int getMinHeight() {
    // FIXME: hardcoded height
    return 60;
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
    GraphicsMode.start2D();
    FontRendererCache.singleton.get("Arial-BOLD-32").draw(getContent());
    GraphicsMode.end2D();
  }
}
