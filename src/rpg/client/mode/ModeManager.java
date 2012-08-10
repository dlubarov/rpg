package rpg.client.mode;

import rpg.client.gfx.widget.winow.WindowManager;
import rpg.util.Logger;

public final class ModeManager {
  private ModeManager() {}

  private static Mode currentMode;

  static {
    currentMode = new LoginMode();
    currentMode.onEnter();
  }

  public static Mode getCurrentMode() {
    return currentMode;
  }

  public static void switchTo(Mode mode) {
    Logger.info("Switching to %s.", mode);
    currentMode.onExit();
    WindowManager.clearChildren();
    currentMode = mode;
    currentMode.onEnter();
  }
}
