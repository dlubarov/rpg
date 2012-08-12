package rpg.client.gfx.widget;

public class VBox extends Container {
  public VBox(Widget... children) {
    super(children);
  }

  @Override public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    int extraSpace = bounds.h() - getMinHeight();
    int numRecipients = numStretchyChildren();
    int y = bounds.y1();
    for (Widget child : children) {
      int h = child.getMinHeight();
      if (child.stretchVertically()) {
        int extraH = extraSpace / numRecipients--;
        extraSpace -= extraH;
        h += extraH;
      }
      child.setBounds(new Bounds(bounds.x1(), y, bounds.x2(), y + h));
      y += h;
    }
    assert y == bounds.y2() : y + " != " + bounds.y2();
  }

  @Override public int getMinWidth() {
    int max = 0;
    for (Widget child : children)
      max = Math.max(max, child.getMinWidth());
    return max;
  }

  @Override public int getMinHeight() {
    int sum = 0;
    for (Widget child : children)
      sum += child.getMinHeight();
    return sum;
  }

  @Override public boolean stretchHorizontally() {
    for (Widget child : children)
      if (!child.stretchHorizontally())
        return false;
    return true;
  }

  @Override public boolean stretchVertically() {
    return numStretchyChildren() > 0;
  }

  private int numStretchyChildren() {
    int n = 0;
    for (Widget child : children)
      if (child.stretchVertically())
        ++n;
    return n;
  }
}
