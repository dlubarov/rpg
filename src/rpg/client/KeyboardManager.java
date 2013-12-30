package rpg.client;

import org.lwjgl.input.Keyboard;
import rpg.client.gfx.widget.Widget;
import rpg.client.gfx.widget.winow.WindowManager;
import rpg.client.mode.ModeManager;

public class KeyboardManager {
  public static void processEvents() {
    while (Keyboard.next()) {
      boolean down = Keyboard.getEventKeyState();
      if (!down) {
        continue;
      }

      int key = Keyboard.getEventKey();
      switch (key) {
        case Keyboard.KEY_ESCAPE:
          System.exit(0);
          break;
        case Keyboard.KEY_F4:
          if (Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA)) {
            System.exit(0);
          }
          break;
      }
      Widget focusedWidget = WindowManager.getFocusedWidget();
      if (focusedWidget != null) {
        focusedWidget.onKeyDown(key);
      }
      ModeManager.getCurrentMode().onKeyDown(key);
    }
  }
}
