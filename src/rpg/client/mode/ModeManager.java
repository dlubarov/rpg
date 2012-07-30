package rpg.client.mode;

import rpg.client.mode.menu.MenuMode;

public final class ModeManager {
  private ModeManager() {}

  private static Mode currentMode;

  static {
    currentMode = new MenuMode();
    currentMode.onEnter();
  }

  public static Mode getCurrentMode() {
    return currentMode;
  }

  public static void switchTo(Mode mode) {
    currentMode.onExit();
    currentMode = mode;
    currentMode.onEnter();
  }
}
