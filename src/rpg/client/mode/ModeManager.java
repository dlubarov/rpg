package rpg.client.mode;

public final class ModeManager {
  private ModeManager() {}

  private static Mode currentMode = new LoginMode();

  public static Mode getCurrentMode() {
    return currentMode;
  }

  public static void switchTo(Mode mode) {
    currentMode.onExit();
    currentMode = mode;
    currentMode.onEnter();
  }
}
