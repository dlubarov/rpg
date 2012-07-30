package rpg.client.gfx.widget;

public class HBox extends Widget {
  private final Widget[] children;

  public HBox(Widget... children) {
    this.children = children;
  }

  @Override
  public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    int extraSpace = bounds.w() - getMinWidth();
    int numRecipients = numStretchyChildren();
    int x = bounds.x1();
    for (Widget child : children) {
      int w = child.getMinWidth();
      if (child.stretchHorizontally()) {
        int extraW = extraSpace / numRecipients--;
        extraSpace -= extraW;
        w += extraW;
      }
      child.setBounds(new Bounds(x, bounds.y1(), x + w, bounds.y2()));
      x += w;
    }
    assert x == bounds.x2() : x + " != " + bounds.x2();
  }

  @Override
  public int getMinWidth() {
    int sum = 0;
    for (Widget child : children)
      sum += child.getMinWidth();
    return sum;
  }

  @Override
  public int getMinHeight() {
    int max = 0;
    for (Widget child : children)
      max = Math.max(max, child.getMinHeight());
    return max;
  }

  @Override
  public boolean stretchHorizontally() {
    return numStretchyChildren() > 0;
  }

  @Override
  public boolean stretchVertically() {
    for (Widget child : children)
      if (!child.stretchVertically())
        return false;
    return true;
  }

  private int numStretchyChildren() {
    int n = 0;
    for (Widget child : children)
      if (child.stretchHorizontally())
        ++n;
    return n;
  }

  @Override
  public void render() {
    for (Widget child : children)
      child.render();
  }
}
