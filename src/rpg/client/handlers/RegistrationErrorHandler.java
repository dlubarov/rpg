package rpg.client.handlers;

import rpg.msg.s2c.RegistrationErrorMessage;

public class RegistrationErrorHandler extends Handler<RegistrationErrorMessage> {
  public static final RegistrationErrorHandler singleton = new RegistrationErrorHandler();

  private RegistrationErrorHandler() {}

  @Override
  public void handle(RegistrationErrorMessage msg) {
    // FIXME handle
  }
}
