package rpg.client.handlers;

import rpg.client.mode.CharacterSetupMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.client.mode.RegistrationMode;
import rpg.msg.s2c.RegistrationAcceptanceMessage;
import rpg.util.Logger;

public class RegistrationAcceptanceHandler extends Handler<RegistrationAcceptanceMessage> {
  public static final RegistrationAcceptanceHandler singleton = new RegistrationAcceptanceHandler();

  @Override
  public void handle(RegistrationAcceptanceMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof RegistrationMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }
    ModeManager.switchTo(new CharacterSetupMode());
  }
}
