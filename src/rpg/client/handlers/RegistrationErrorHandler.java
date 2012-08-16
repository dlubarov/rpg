package rpg.client.handlers;

import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.client.mode.RegistrationMode;
import rpg.net.msg.s2c.RegistrationErrorMessage;
import rpg.util.Logger;

public class RegistrationErrorHandler extends Handler<RegistrationErrorMessage> {
  public static final RegistrationErrorHandler singleton = new RegistrationErrorHandler();

  private RegistrationErrorHandler() {}

  @Override public void handle(RegistrationErrorMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof RegistrationMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    RegistrationMode registrationMode = (RegistrationMode) currentMode;
    registrationMode.receiveError(msg.reason);
  }
}
