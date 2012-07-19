package rpg.client.mode;

public class ModeManager {
  private static Mode currentMode;

  public static void switchTo(Mode mode) {
    currentMode = mode;
  }
}
