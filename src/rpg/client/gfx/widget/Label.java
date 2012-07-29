package rpg.client.gfx.widget;

import rpg.client.gfx.font.FontRendererCache;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

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
    glPushMatrix();
    glTranslated(bounds.x1(), bounds.y2(), 0);
    FontRendererCache.singleton.get("Arial-32").draw(getContent());
    glPopMatrix();
  }
}
