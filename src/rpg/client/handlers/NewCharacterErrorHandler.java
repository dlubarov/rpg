package rpg.client.handlers;

import rpg.client.mode.CharacterSetupMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.net.msg.s2c.NewCharacterErrorMessage;
import rpg.util.Logger;

public class NewCharacterErrorHandler extends Handler<NewCharacterErrorMessage> {
  public static final NewCharacterErrorHandler singleton = new NewCharacterErrorHandler();

  private NewCharacterErrorHandler() {}

  @Override public void handle(NewCharacterErrorMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof CharacterSetupMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    CharacterSetupMode characterSetupMode = (CharacterSetupMode) currentMode;
    characterSetupMode.receivedError(msg.reason);
  }
}
