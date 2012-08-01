package rpg.client.gfx.widget.winow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import rpg.util.Logger;

public final class WindowManager {
  private WindowManager() {}

  private static final Deque<ChildWindow> childWindows = new ArrayDeque<ChildWindow>();
  private static ChildWindow focusedWindow = null;

  private static Window draggedWindow = null;
  private static int dragX, dragY;

  public static void onLeftMouseDown(int x, int y) {
    // TODO: check buttons
    draggedWindow = focusedWindow = null;
    for (ChildWindow window : childWindowsReversed())
      if (window.inWindow(x, y)) {
        bringToTop(window);
        if (window.inTitleBar(x, y)) {
          draggedWindow = window;
          dragX = x - window.x1();
          dragY = y - window.y1();
        }
        return;
      }
    if (RootWindow.singleton.inTitleBar(x, y)) {
      draggedWindow = RootWindow.singleton;
      dragX = Mouse.getX();
      // win.x = (win.x + mouse.x - dragX)
      dragY = Display.getHeight() - Mouse.getY();
      Logger.info("Dragging from (%d, %d)", dragX, dragY);
    }
  }

  private static List<ChildWindow> childWindowsReversed() {
    List<ChildWindow> reversed = new ArrayList<ChildWindow>(childWindows);
    Collections.reverse(reversed);
    return reversed;
  }

  public static void onLeftMouseUp(int x, int y) {
    draggedWindow = null;
  }

  public static void addChild(ChildWindow window) {
    childWindows.addLast(window);
    focusedWindow = window;
  }

  public static boolean removeChild(ChildWindow window) {
    if (window == draggedWindow)
      draggedWindow = null;
    return childWindows.remove(window);
  }

  public static void bringToTop(ChildWindow window) {
    removeChild(window);
    addChild(window);
  }

  public static ChildWindow getFocusedWindow() {
    return focusedWindow;
  }

  public static void render() {
    if (draggedWindow != null) {
      int mouseX = Mouse.getX(),
          mouseY = Display.getHeight() - Mouse.getY();
      if (draggedWindow instanceof RootWindow) {
        int dx = mouseX - dragX,
            dy = mouseY - dragY;
        Display.setLocation(Display.getX() + dx, Display.getY() + dy);
      } else
        ((ChildWindow) draggedWindow).moveTo(mouseX - dragX, mouseY - dragY);
    }
    for (ChildWindow window : childWindows)
      window.render();
  }
}
