package rpg.client.gfx.widget;

public class FixedVSpace extends Widget {
  private final int amount;

  public FixedVSpace(int amount) {
    this.amount = amount;
  }

  @Override public int getMinWidth() {
    return 0;
  }

  @Override public int getMinHeight() {
    return amount;
  }

  @Override public boolean stretchHorizontally() {
    return true;
  }

  @Override public boolean stretchVertically() {
    return false;
  }

  @Override public void render() {}
}
