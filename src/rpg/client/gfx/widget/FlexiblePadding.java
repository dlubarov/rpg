package rpg.client.gfx.widget;

public class FlexiblePadding extends Container {
  public FlexiblePadding(Widget content) {
    super(content);
  }

  @Override
  public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    int extraW = bounds.w() - children[0].getMinWidth(),
        extraH = bounds.h() - children[0].getMinHeight();
    int left = extraW / 2, right = extraW - left,
        top = extraH / 2, bottom = extraH - top;
    children[0].setBounds(new Bounds(
        bounds.x1() + left, bounds.y1() + top,
        bounds.x2() - right, bounds.y2() - bottom));
  }

  @Override
  public int getMinWidth() {
    return children[0].getMinWidth();
  }

  @Override
  public int getMinHeight() {
    return children[0].getMinHeight();
  }

  @Override
  public boolean stretchHorizontally() {
    return true;
  }

  @Override
  public boolean stretchVertically() {
    return true;
  }
}
