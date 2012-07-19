package rpg.server.handlers;

import rpg.client.handlers.Handler;
import rpg.msg.ConfirmationMessage;
import rpg.net.RetryQueue;

public class ConfirmationHandler extends Handler<ConfirmationMessage> {
  public static final ConfirmationHandler singleton = new ConfirmationHandler();

  private ConfirmationHandler() {}

  @Override
  public void handle(ConfirmationMessage msg) {
    RetryQueue.stopRetrying(msg.uuid);
  }
}
