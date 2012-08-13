package rpg.client.handlers;

import rpg.client.mode.LoginMode;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.msg.s2c.LoginErrorMessage;
import rpg.util.Logger;

public class LoginErrorHandler extends Handler<LoginErrorMessage> {
  public static final LoginErrorHandler singleton = new LoginErrorHandler();

  private LoginErrorHandler() {}

  @Override public void handle(LoginErrorMessage msg) {
    Mode currentMode = ModeManager.getCurrentMode();
    if (!(currentMode instanceof LoginMode)) {
      Logger.warning("Received %s while in %s.", msg, currentMode);
      return;
    }

    LoginMode loginMode = (LoginMode) currentMode;
    loginMode.receiveError(msg.reason);
  }
}
