package rpg.client.gfx.widget.winow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import rpg.util.Logger;

public class WindowManager {
  public static final WindowManager singleton = new WindowManager();

  private final Deque<ChildWindow> childWindows;
  private ChildWindow focusedWindow = null;

  private Window draggedWindow = null;
  private int dragX, dragY;

  private WindowManager() {
    childWindows = new ArrayDeque<ChildWindow>();
  }

  public void onLeftMouseDown(int x, int y) {
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

  private List<ChildWindow> childWindowsReversed() {
    List<ChildWindow> reversed = new ArrayList<ChildWindow>(childWindows);
    Collections.reverse(reversed);
    return reversed;
  }

  public void onLeftMouseUp(int x, int y) {
    draggedWindow = null;
  }

  public void addChild(ChildWindow window) {
    childWindows.addLast(window);
    focusedWindow = window;
  }

  public void removeChild(ChildWindow window) {
    if (!childWindows.remove(window)) {
      throw new AssertionError();
    }
    if (window == draggedWindow) {
      draggedWindow = null;
    }
  }

  public void bringToTop(ChildWindow window) {
    removeChild(window);
    addChild(window);
  }

  public ChildWindow getFocusedWindow() {
    return focusedWindow;
  }

  public void render() {
    if (draggedWindow != null) {
      int mouseX = Mouse.getX(),
          mouseY = Display.getHeight() - Mouse.getY();
      System.out.println(mouseX + ", " + mouseY);
      if (draggedWindow instanceof RootWindow) {
        int dx = mouseX - dragX,
            dy = mouseY - dragY;
        //dx = Mouse.getDX();
        //dy = -Mouse.getDY();
        Display.setLocation(Display.getX() + dx, Display.getY() + dy);
      } else {
        ((ChildWindow) draggedWindow).moveTo(mouseX - dragX, mouseY - dragY);
      }
    }
    for (ChildWindow window : childWindows)
      window.render();
  }
}
