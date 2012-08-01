package rpg.client.handlers;

import rpg.msg.s2c.WelcomeMessage;

public class WelcomeHandler extends Handler<WelcomeMessage> {
  public static final WelcomeHandler singleton = new WelcomeHandler();

  private WelcomeHandler() {}

  @Override
  public void handle(WelcomeMessage msg) {
    // FIXME handle
  }
}
