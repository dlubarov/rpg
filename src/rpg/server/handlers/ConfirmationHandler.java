package rpg.server.handlers;

import rpg.net.RetryQueue;
import rpg.net.msg.ConfirmationMessage;
import rpg.server.Session;

public class ConfirmationHandler extends Handler<ConfirmationMessage> {
  public static final ConfirmationHandler singleton = new ConfirmationHandler();

  private ConfirmationHandler() {}

  @Override public void handle(ConfirmationMessage msg, Session clientSession) {
    RetryQueue.stopRetrying(msg.uuid);
  }
}
