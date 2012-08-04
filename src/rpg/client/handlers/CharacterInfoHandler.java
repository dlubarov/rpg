package rpg.client.handlers;

import rpg.client.mode.CharacterSelectMode;
import rpg.client.mode.LoginMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.msg.s2c.CharacterInfoMessage;
import rpg.util.Logger;

public class CharacterInfoHandler extends Handler<CharacterInfoMessage> {
  public static final CharacterInfoHandler singleton = new CharacterInfoHandler();

  private CharacterInfoHandler() {}

  @Override
  public void handle(CharacterInfoMessage msg) {
    Mode mode = ModeManager.getCurrentMode();
    if (!(mode instanceof LoginMode)) {
      Logger.warning("Received %s while in %s", msg, mode);
      return;
    }

    CharacterSelectMode characterSelectMode = new CharacterSelectMode(msg.parts);
    ModeManager.switchTo(characterSelectMode);
  }
}
