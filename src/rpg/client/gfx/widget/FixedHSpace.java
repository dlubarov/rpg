package rpg.client.gfx.widget;

public class FixedHSpace extends Widget {
  private final int amount;

  public FixedHSpace(int amount) {
    this.amount = amount;
  }

  @Override public int getMinWidth() {
    return amount;
  }

  @Override public int getMinHeight() {
    return 0;
  }

  @Override public boolean stretchHorizontally() {
    return false;
  }

  @Override public boolean stretchVertically() {
    return false;
  }

  @Override public void render() {}
}
