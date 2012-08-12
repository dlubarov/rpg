package rpg.client.mode;

import rpg.client.gfx.widget.Widget;
import rpg.client.gfx.widget.winow.RootWindow;

public abstract class Mode2D extends Mode {
  @Override public abstract Widget getContent();

  @Override public final void onLeftMouse(int x, int y) {
    getContent().onClick(x, y);
  }

  @Override public final void onRightMouse(int x, int y) {}

  @Override public final void render() {
    getContent().render();
  }

  protected void setContentBounds() {
    getContent().setBounds(RootWindow.singleton.bounds());
  }
}
