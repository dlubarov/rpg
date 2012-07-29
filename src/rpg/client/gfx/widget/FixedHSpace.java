package rpg.client.gfx.widget;

public class FixedHSpace extends Widget {
  private final int size;

  public FixedHSpace(int size) {
    this.size = size;
  }

  @Override
  public int getMinWidth() {
    return size;
  }

  @Override
  public int getMinHeight() {
    return 0;
  }

  @Override
  public boolean stretchHorizontally() {
    return false;
  }

  @Override
  public boolean stretchVertically() {
    return true;
  }

  @Override
  public void render() {}
}
