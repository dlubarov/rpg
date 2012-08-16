package rpg.client.handlers;

import rpg.client.mode.LoginMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.msg.s2c.CharacterInfoMessage;
import rpg.util.Logger;

public class CharacterInfoHandler extends Handler<CharacterInfoMessage> {
  public static final CharacterInfoHandler singleton = new CharacterInfoHandler();

  private CharacterInfoHandler() {}

  @Override public void handle(CharacterInfoMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof LoginMode)) {
      Logger.warning("Received %s while in %s", msg, currentMode);
      return;
    }

    ((LoginMode) currentMode).receiveSuccess(msg.parts);
  }
}
