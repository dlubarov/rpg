package rpg.client.gfx.widget.winow;

import java.util.ArrayDeque;
import java.util.Deque;

public class ChildManager {
  public static final ChildManager singleton = new ChildManager();

  private final Deque<ChildWindow> childWindows;

  public ChildManager() {
    childWindows = new ArrayDeque<ChildWindow>();
  }

  public void addChild(ChildWindow window) {
    childWindows.addLast(window);
  }

  public void removeChild(ChildWindow window) {
    if (!childWindows.remove(window))
      throw new AssertionError();
  }

  public void bringToTop(ChildWindow window) {
    removeChild(window);
    addChild(window);
  }

  public void render() {
    for (ChildWindow window : childWindows)
      window.render();
  }
}
