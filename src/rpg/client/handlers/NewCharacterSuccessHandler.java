package rpg.client.handlers;

import rpg.client.mode.CharacterSetupMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.NewCharacterSuccessMessage;
import rpg.util.Logger;

public class NewCharacterSuccessHandler extends Handler<NewCharacterSuccessMessage> {
  public static final NewCharacterSuccessHandler singleton = new NewCharacterSuccessHandler();

  private NewCharacterSuccessHandler() {}

  @Override public void handle(NewCharacterSuccessMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof CharacterSetupMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    CharacterSetupMode characterSetupMode = (CharacterSetupMode) currentMode;
    characterSetupMode.receivedSuccess(msg.characterSummary);
  }
}
