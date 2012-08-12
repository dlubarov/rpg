package rpg.client.gfx.widget;

public abstract class Container extends Widget {
  protected final Widget[] children;

  protected Container(Widget... children) {
    this.children = children;
  }

  @Override public final void onClick(int x, int y) {
    for (Widget child : children)
      if (child.bounds.contains(x, y))
        child.onClick(x, y);
  }

  @Override public final void render() {
    for (Widget child : children)
      child.render();
  }
}
