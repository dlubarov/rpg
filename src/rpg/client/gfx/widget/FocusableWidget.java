package rpg.client.gfx.widget;

import rpg.client.gfx.widget.winow.WindowManager;

public abstract class FocusableWidget extends Widget {
  @Override public void onClick(int x, int y) {
    WindowManager.makeFocused(this);
  }

  @Override public abstract void onKeyDown(int key);
}
