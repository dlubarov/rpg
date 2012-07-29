package rpg.client.gfx.widget;

public class FixedPadding extends Widget {
  private final Widget child;
  private final int left, right, top, bottom;

  public FixedPadding(Widget child, int left, int right, int top, int bottom) {
    this.child = child;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
  }

  public FixedPadding(Widget child, int padding) {
    this(child, padding, padding, padding, padding);
  }

  @Override
  public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    child.setBounds(new Bounds(
        bounds.x1() + left,
        bounds.y1() + top,
        bounds.x2() - right,
        bounds.y2() - bottom));
  }

  @Override
  public int getMinWidth() {
    return left + child.getMinWidth() + right;
  }

  @Override
  public int getMinHeight() {
    return top + child.getMinHeight() + bottom;
  }

  @Override
  public boolean stretchHorizontally() {
    return child.stretchHorizontally();
  }

  @Override
  public boolean stretchVertically() {
    return child.stretchVertically();
  }

  @Override
  public void render() {
    child.render();
  }
}
