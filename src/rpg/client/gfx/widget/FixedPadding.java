package rpg.client.gfx.widget;

public class FixedPadding extends Container {
  private final int left, right, top, bottom;

  public FixedPadding(Widget content, int left, int right, int top, int bottom) {
    super(content);
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
  }

  public FixedPadding(Widget content, int padding) {
    this(content, padding, padding, padding, padding);
  }

  @Override
  public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    children[0].setBounds(new Bounds(
        bounds.x1() + left, bounds.y1() + top,
        bounds.x2() - right, bounds.y2() - bottom));
  }

  @Override
  public int getMinWidth() {
    return left + children[0].getMinWidth() + right;
  }

  @Override
  public int getMinHeight() {
    return top + children[0].getMinHeight() + bottom;
  }

  @Override
  public boolean stretchHorizontally() {
    return children[0].stretchHorizontally();
  }

  @Override
  public boolean stretchVertically() {
    return children[0].stretchVertically();
  }
}
