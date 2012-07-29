package rpg.client.gfx.widget;

import rpg.client.gfx.font.FontRendererCache;

public class TextBox extends Widget {
  private String content;

  public TextBox(String initialContent) {
    content = initialContent;
  }

  @Override
  public int getMinWidth() {
    return 100;
  }

  @Override
  public int getMinHeight() {
    // FIXME: use font bounds
    return 60;
  }

  @Override
  public boolean stretchHorizontally() {
    return true;
  }

  @Override
  public boolean stretchVertically() {
    return false;
  }

  @Override
  public void render() {
    FontRendererCache.singleton.get("Arial-BOLD-32").draw(content);
  }
}
