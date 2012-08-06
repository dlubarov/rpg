package rpg.client.mode;

import rpg.client.gfx.widget.FlexibleSpace;
import rpg.client.gfx.widget.Widget;

public abstract class Mode {
  public void onEnter() {}
  public void onExit() {}
  public void logic() {}

  public void onKeyDown(int key) {}
  public void onLeftMouse(int x, int y) {}
  public void onRightMouse(int x, int y) {}

  public abstract void render();

  public Widget getContent() {
    return FlexibleSpace.singleton;
  }

  public boolean isCurrent() {
    return ModeManager.getCurrentMode() == this;
  }
}
