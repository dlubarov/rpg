package rpg.client.mode;

import rpg.client.gfx.widget.Widget;
import rpg.client.gfx.widget.winow.RootWindow;

public abstract class Mode2D extends Mode {
  public final Widget content;

  protected Mode2D(Widget content) {
    this.content = content;
    content.setBounds(RootWindow.singleton.bounds());
  }

  @Override
  public final void onLeftMouse(int x, int y) {
    content.onClick(x, y);
  }

  @Override
  public final void onRightMouse(int x, int y) {}

  @Override
  public final void render() {
    content.render();
  }

  @Override
  public final Widget getContent() {
    return content;
  }
}
