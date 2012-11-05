package rpg.client.gfx.widget.winow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import rpg.client.gfx.widget.Widget;
import rpg.util.Logger;

public final class WindowManager {
  private WindowManager() {}

  private static final Deque<ChildWindow> childWindows = new ArrayDeque<ChildWindow>();
  private static ChildWindow focusedWindow = null;
  private static Widget focusedWidget = null;

  private static Window draggedWindow = null;
  private static int dragX, dragY;

  public static void onLeftMouseDown(int x, int y) {
    // TODO: check buttons
    onLeftMouseDownDragging(x, y);
    ChildWindow focusedChild = childFor(x, y);
    if (focusedChild != null) {
      focusedChild.content.onClick(x, y);
      focusedChild.onClick(x, y);
    }
    RootWindow.singleton.onClick(x, y);
  }

  private static void onLeftMouseDownDragging(int x, int y) {
    draggedWindow = focusedWindow = null;
    focusedWidget = null;

    ChildWindow focusedChild = childFor(x, y);
    if (focusedChild != null) {
      bringToTop(focusedChild);
      if (focusedChild.inTitleBar(x, y)) {
        draggedWindow = focusedChild;
        dragX = x - focusedChild.x1();
        dragY = y - focusedChild.y1();
      }
    } else if (RootWindow.singleton.inTitleBar(x, y)) {
      draggedWindow = RootWindow.singleton;
      dragX = Mouse.getX();
      dragY = Display.getHeight() - Mouse.getY();
      Logger.debug("Dragging from (%d, %d)", dragX, dragY);
    }
  }

  private static ChildWindow childFor(int x, int y) {
    for (ChildWindow window : childWindowsReversed())
      if (window.inWindow(x, y))
        return window;
    return null;
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

  public static void clearChildren() {
    childWindows.clear();
    focusedWindow = null;
    if (draggedWindow instanceof ChildWindow)
      draggedWindow = null;
  }

  public static void bringToTop(ChildWindow window) {
    removeChild(window);
    addChild(window);
  }

  public static ChildWindow getFocusedWindow() {
    return focusedWindow;
  }

  public static void makeFocused(Widget widget) {
    focusedWidget = widget;
  }

  public static Widget getFocusedWidget() {
    return focusedWidget;
  }

  public static void render() {
    if (draggedWindow != null) {
      int mouseX = Mouse.getX(),
          mouseY = Display.getHeight() - Mouse.getY();
      if (draggedWindow instanceof RootWindow) {
        int dx = mouseX - dragX,
            dy = mouseY - dragY;
        Display.setLocation(Display.getX() + dx, Display.getY() + dy);
        recreateMouse();
      } else
        ((ChildWindow) draggedWindow).moveTo(mouseX - dragX, mouseY - dragY);
    }
    for (ChildWindow window : childWindows)
      window.render();
  }

  /**
   * LWJGL seems to cache mouse coordinates and update them only when the mouse moves. Since these
   * coordinates are relative to the display, getX()/getY() do not behave correctly when the mouse
   * is still but the window is moving. This hack forces LWJGL to update the cached mouse position
   * even if the mouse has not moved.
   *
   * See this thread for details: http://lwjgl.org/forum/index.php/topic,4695.0.html
   */
  private static void recreateMouse() {
    Mouse.destroy();
    try {
      Mouse.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
  }
}
