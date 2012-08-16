package rpg.client.handlers;

import rpg.client.mode.CharacterSelectMode;
import rpg.client.mode.GameMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.msg.s2c.WelcomeMessage;
import rpg.util.Logger;

public class WelcomeHandler extends Handler<WelcomeMessage> {
  public static final WelcomeHandler singleton = new WelcomeHandler();

  private WelcomeHandler() {}

  @Override public void handle(WelcomeMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof CharacterSelectMode)) {
      Logger.warning("Expected to be in character select mode, was in %s.", currentMode);
      return;
    }

    ModeManager.switchTo(new GameMode(msg));
  }
}
