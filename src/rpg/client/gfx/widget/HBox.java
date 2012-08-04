package rpg.client.gfx.widget;

import rpg.util.Logger;

public class HBox extends Container {
  public HBox(Widget... children) {
    super(children);
  }

  @Override
  public void setBounds(Bounds bounds) {
    super.setBounds(bounds);
    int extraSpace = bounds.w() - getMinWidth();
    int numRecipients = numStretchyChildren();
    if (extraSpace > 0 && numRecipients == 0)
      Logger.error("HBox has no stretchy children");
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
}
