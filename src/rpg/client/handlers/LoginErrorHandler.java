package rpg.client.handlers;

import rpg.msg.s2c.LoginErrorMessage;

public class LoginErrorHandler extends Handler<LoginErrorMessage> {
  public static final LoginErrorHandler singleton = new LoginErrorHandler();

  private LoginErrorHandler() {}

  @Override
  public void handle(LoginErrorMessage msg) {
    // FIXME handle
  }
}
