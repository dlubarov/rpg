package rpg.client.gfx.widget;

public abstract class Container extends Widget {
  protected final Widget[] children;

  protected Container(Widget... children) {
    this.children = children;
  }

  @Override
  public final void onClick(int x, int y) {
    for (Widget child : children)
      if (child.bounds.contains(x, y))
        child.onClick(x, y);
  }

  @Override
  public final Widget getWidget(String name) {
    for (Widget child : children) {
      Widget widget = child.getWidget(name);
      if (widget != null)
        return widget;
    }
    return null;
  }

  @Override
  public final String getValue(String name) {
    for (Widget child : children) {
      String childValue = child.getValue(name);
      if (childValue != null)
        return childValue;
    }
    return null;
  }

  @Override
  public final void render() {
    for (Widget child : children)
      child.render();
  }
}
