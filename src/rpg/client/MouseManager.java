package rpg.client;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import rpg.client.gfx.widget.winow.WindowManager;
import rpg.client.mode.ModeManager;

public class MouseManager {
  public static void processEvents() {
    while (Mouse.next()) {
      if (Mouse.getEventButton() == -1)
        onCursorMotion();
      else if (Mouse.getEventButtonState())
        onButtonDown();
      else
        onButtonUp();
    }
  }

  private static void onButtonDown() {
    int x = Mouse.getEventX(), y = Display.getHeight() - Mouse.getEventY();
    switch (Mouse.getEventButton()) {
      case 0:
        System.out.printf("Left down at (%d, %d).\n", x, y);
        WindowManager.onLeftMouseDown(x, y);
        ModeManager.getCurrentMode().onLeftMouse(x, y);
        break;
      case 1:
        ModeManager.getCurrentMode().onRightMouse(x, y);
        break;
    }
  }

  private static void onButtonUp() {
    int x = Mouse.getEventX(), y = Display.getHeight() - Mouse.getEventY();
    switch (Mouse.getEventButton()) {
      case 0:
        System.out.printf("Left up at (%d, %d).\n", x, y);
        WindowManager.onLeftMouseUp(x, y);
        break;
      case 1:
        break;
    }
  }

  private static void onCursorMotion() {
    WindowManager.onCursorMotion();
  }
}
