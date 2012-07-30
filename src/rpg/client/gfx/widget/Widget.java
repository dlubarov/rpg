package rpg.client.gfx.widget;

public abstract class Widget {
  protected Bounds bounds;

  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  public abstract int getMinWidth();
  public abstract int getMinHeight();

  public abstract boolean stretchHorizontally();
  public abstract boolean stretchVertically();

  public abstract void render();

  public Widget pad(int amount) {
    return new FixedPadding(this, amount);
  }
}
