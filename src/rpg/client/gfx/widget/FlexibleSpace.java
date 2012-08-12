package rpg.client.gfx.widget;

public class FlexibleSpace extends Widget {
  public static final FlexibleSpace singleton = new FlexibleSpace();

  private FlexibleSpace() {}

  @Override public int getMinWidth() {
    return 0;
  }

  @Override public int getMinHeight() {
    return 0;
  }

  @Override public boolean stretchHorizontally() {
    return true;
  }

  @Override public boolean stretchVertically() {
    return true;
  }

  @Override public void render() {}
}
