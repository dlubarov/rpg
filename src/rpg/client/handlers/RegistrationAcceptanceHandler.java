package rpg.client.handlers;

import rpg.msg.s2c.RegistrationAcceptanceMessage;

public class RegistrationAcceptanceHandler extends Handler<RegistrationAcceptanceMessage> {
  public static final RegistrationAcceptanceHandler singleton = new RegistrationAcceptanceHandler();
  @Override
  public void handle(RegistrationAcceptanceMessage msg) {
    // FIXME: Handle message.
  }
}
